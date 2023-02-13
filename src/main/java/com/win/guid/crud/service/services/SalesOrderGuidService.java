package com.win.guid.crud.service.services;

import com.win.guid.crud.service.common.MsgProcessorHelper;
import com.win.guid.crud.service.models.GuidMaster;
import com.win.guid.crud.service.models.SalesOrderGuidRequest;
import com.win.guid.crud.service.models.SalesOrderGuidUpdateRequest;
import com.win.guid.crud.service.repositories.GuidMasterRepository;
import com.win.guid.crud.service.common.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.win.guid.crud.service.common.Constants.PROGRAM_NAME;

@Service
public class SalesOrderGuidService {

    @Value("${spring.datasource.username}")
    String mUserName;

    Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GuidMasterRepository mGuidMasterRepository;

    @Autowired
    GuidRandomGeneratorService mGuidRandomGeneratorService;

    @Autowired
    private MsgProcessorHelper mMsgProcessorHelper;

    @Autowired
    Utility mUtility;

    public SalesOrderGuidService(final GuidMasterRepository pGuidMasterRepository) {
        mGuidMasterRepository = pGuidMasterRepository;
    }

    /**
     * Get the Sales Order Guid
     *
     * @param pSalesOrderGuidRequest   (String)
     * @return (GuidMasterRecord)
     **/
    public GuidMaster getSalesOrderGuidByCompanyNumberAndByCustomerTypeAndByCustomerAccountAndByObjectTypeAndByOrderNumber
        (final SalesOrderGuidRequest pSalesOrderGuidRequest) throws Exception {

        String lCompanyNumber = pSalesOrderGuidRequest.getCompanyNumber();
        String lCustomerType = pSalesOrderGuidRequest.getCustomerType();
        String lCustomerAccount = pSalesOrderGuidRequest.getCustomerAccount();
        String lObjectType = pSalesOrderGuidRequest.getObjectType();
        String lOrderNumber = pSalesOrderGuidRequest.getOrderNumber();

        GuidMaster lGuidMasterRecord = null;

        try {
            if (pSalesOrderGuidRequest != null) {
                lGuidMasterRecord = mGuidMasterRepository.
                    findFirstByCompanyNumberAndCustomerTypeAndCustomerAccountAndObjectTypeAndOrderNumberOrderByAddedTimestampDesc(
                                lCompanyNumber, lCustomerType, lCustomerAccount, lObjectType, lOrderNumber);
                }
            } catch (final Exception lException) {
            mLogger.error("Unable to find the guid for the sales order {} CompanyNumber {} CustomerType {} ObjectType {} due to exception: {}", lOrderNumber, lCompanyNumber, lCustomerType, lObjectType, lException);
            throw lException;
        }

        return lGuidMasterRecord;

    }

    /**
     * Get the Sales Order Guid
     *
     * @param pGuidMasterId   (String)
     * @return (GuidMasterRecord)
     **/
    public GuidMaster getSalesOrderGuidByGuidMasterId(final String pGuidMasterId) throws Exception {

        GuidMaster lGuidMasterRecord = null;

        try {
            if (StringUtils.hasText(pGuidMasterId)) {
                long wkGuidMasterId = Long.parseLong(pGuidMasterId);
                lGuidMasterRecord = mGuidMasterRepository.findFirstByGuidMasterId(wkGuidMasterId);
            }
        } catch (final Exception lException) {
            mLogger.error("Unable to find the guid for the guid master id {}", pGuidMasterId, lException);
            throw lException;
        }

        return lGuidMasterRecord;

    }

    /**
     * Insert the Sales Order Guid
     *
     * @param pSalesOrderGuidRequest   (String)
     * @return (GuidMasterRecord)
     **/

    public GuidMaster CreateSalesOrderGuid(final SalesOrderGuidRequest pSalesOrderGuidRequest) throws Exception {

        mLogger.info("Adding Sales Order Guid To DB");

        String lCompanyNumber = pSalesOrderGuidRequest.getCompanyNumber();
        String lCustomerType = pSalesOrderGuidRequest.getCustomerType();
        String lCustomerAccount = pSalesOrderGuidRequest.getCustomerAccount();
        String lObjectType = pSalesOrderGuidRequest.getObjectType();
        String lOrderNumber = pSalesOrderGuidRequest.getOrderNumber();

        GuidMaster lSavedGuidMaster = null;

        try {
            if (pSalesOrderGuidRequest != null) {
                String wkGuid = mGuidRandomGeneratorService.GuidRandomGeneratorV4();
                if (wkGuid != null) {
                    // save to Guid Master Table
                    final GuidMaster lGuidMasterToSave = new GuidMaster();
                    lGuidMasterToSave.setGuid(wkGuid);  //have to generate the guid
                    lGuidMasterToSave.setCompanyNumber(lCompanyNumber);
                    lGuidMasterToSave.setObjectType(lObjectType);
                    lGuidMasterToSave.setCustomerType(lCustomerType);
                    lGuidMasterToSave.setCustomerAccount(lCustomerAccount);
                    lGuidMasterToSave.setOrderNumber(lOrderNumber);
                    lGuidMasterToSave.setShipmentNumber(0);
                    lGuidMasterToSave.setPaymentNumber(0);
                    lGuidMasterToSave.setAddedByUser(mUserName);
                    lGuidMasterToSave.setAddedByProgram(PROGRAM_NAME);
                    lGuidMasterToSave.setChangedByUser(mUserName);
                    lGuidMasterToSave.setChangedByProgram(PROGRAM_NAME);
                    lSavedGuidMaster = mGuidMasterRepository.save(lGuidMasterToSave);
                    mLogger.debug("Added Sales Order Guid to DB : {}", lSavedGuidMaster);
                } else {
                    mLogger.info("Unable to get a unique Guid after 10 tries so Sales order Guid was not created: {} CompanyNumber {} CustomerType {} CustomerAccount {} ObjectType {} due to exception: {}", lOrderNumber, lCompanyNumber, lCustomerType, lCustomerAccount, lObjectType);
                    mMsgProcessorHelper.sendErrorEmail("Unable to get a unique Guid so Sales Order Guid was not created: " + pSalesOrderGuidRequest);
                }
            }
        } catch (final Exception lException) {
            mLogger.error("Unable to insert the guid for the sales order {} CompanyNumber {} CustomerType {} CustomerAccount {} ObjectType {} due to exception: {}", lOrderNumber, lCompanyNumber, lCustomerType, lCustomerAccount, lObjectType, lException);
            mMsgProcessorHelper.sendExceptionErrorEmail(lException,
                    "Unable to insert the guid for the sales order :  " + pSalesOrderGuidRequest);
        }

        return lSavedGuidMaster;

    }

        /**
        * Delete Sales Order Guid
        *
        * @param pGuidMasterRecord   (GuidMaster)
        * @return boolean
        **/

public boolean DeleteSalesOrderGuid(final SalesOrderGuidRequest pSalesOrderGuidRequest, final GuidMaster pGuidMasterRecord) throws Exception {

        mLogger.info("Deleting Sales Order Guid From DB");
        boolean lSuccess = false;

    try {
            if (pGuidMasterRecord != null)  {
                mGuidMasterRepository.delete(pGuidMasterRecord);
                lSuccess = true;
            } else {
                mLogger.info("Delete request parameters are invalid, Sales order Guid was not deleted");
                mMsgProcessorHelper.sendErrorEmail("Unable to delete the guid for the sales order :  " + pSalesOrderGuidRequest);
            }
        } catch (final Exception lException) {
            mLogger.error("Unable to delete the guid for the sales order GuidMasterRecord {} due to exception: {}", pSalesOrderGuidRequest, lException);
            mMsgProcessorHelper.sendExceptionErrorEmail(lException,
                "Unable to delete the guid for the sales order :  " + pSalesOrderGuidRequest);
            throw lException;
        }

        return lSuccess;

        }

    /**
     * Update Sales Order Guid
     * @param pSalesOrderGuidUpdateRequest (SalesOrderGuidRequestUpdate)
     * @param pGuidMasterRecord   (GuidMaster)
     * @return boolean
     **/

    public boolean UpdateSalesOrderGuid(final SalesOrderGuidUpdateRequest pSalesOrderGuidUpdateRequest,
        final GuidMaster pGuidMasterRecord) throws Exception {

        mLogger.info("Updating Sales Order Guid in DB");
        boolean lSuccess = false;

        String lCompanyNumber = pSalesOrderGuidUpdateRequest.getCompanyNumber();
        String lCustomerType = pSalesOrderGuidUpdateRequest.getCustomerType();
        String lCustomerAccount = pSalesOrderGuidUpdateRequest.getCustomerAccount();
        String lObjectType = pSalesOrderGuidUpdateRequest.getObjectType();
        String lOrderNumber = pSalesOrderGuidUpdateRequest.getOrderNumber();

        try {
            if (pSalesOrderGuidUpdateRequest != null && pGuidMasterRecord != null)  {
                //GuidMaster lGuidMasterToSave = pGuidMasterRecord;
                //final GuidMasterUpdate lGuidMasterToSave = new GuidMasterUpdate();
                final GuidMaster lGuidMasterToSave = new GuidMaster();
                lGuidMasterToSave.setGuidMasterId(pGuidMasterRecord.getGuidMasterId());
                lGuidMasterToSave.setGuid(pGuidMasterRecord.getGuid());
                lGuidMasterToSave.setCompanyNumber(lCompanyNumber);
                lGuidMasterToSave.setObjectType(lObjectType);
                lGuidMasterToSave.setCustomerType(lCustomerType);
                lGuidMasterToSave.setCustomerAccount(lCustomerAccount);
                lGuidMasterToSave.setOrderNumber(lOrderNumber);
                lGuidMasterToSave.setShipmentNumber(pGuidMasterRecord.getShipmentNumber());
                lGuidMasterToSave.setPaymentNumber(pGuidMasterRecord.getPaymentNumber());
                lGuidMasterToSave.setAddedByUser(pGuidMasterRecord.getAddedByUser());
                lGuidMasterToSave.setAddedByProgram(pGuidMasterRecord.getAddedByProgram());
                lGuidMasterToSave.setChangedByUser(pGuidMasterRecord.getChangedByUser());
                lGuidMasterToSave.setChangedByProgram(pGuidMasterRecord.getChangedByProgram());
                mGuidMasterRepository.save(lGuidMasterToSave);
                lSuccess = true;
            } else {
                mLogger.info("Update request parameters are invalid, Sales order Guid was not updated");
                mMsgProcessorHelper.sendErrorEmail("Unable to update the guid for the sales order :  " + pSalesOrderGuidUpdateRequest);
            }
        } catch (final Exception lException) {
            mLogger.error("Unable to update the guid for the sales order GuidMasterRecord {} due to exception: {}", pGuidMasterRecord, lException);
            mMsgProcessorHelper.sendExceptionErrorEmail(lException,
                    "Unable to update the guid for the sales order :  " + pSalesOrderGuidUpdateRequest);
            throw lException;
        }

        return lSuccess;

    }

}