package com.win.guid.crud.service.controllers;

import com.win.guid.crud.service.models.SalesOrderGuidRequest;
import com.win.guid.crud.service.models.SalesOrderGuidUpdateRequest;
import com.win.guid.crud.service.models.WinResponse;
//import com.winsupply.w2020.wire.WinResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.win.guid.crud.service.models.GuidMaster;
import com.win.guid.crud.service.services.SalesOrderGuidService;
import com.win.guid.crud.service.services.ValidateSalesOrderGuidService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/guids")
public class SalesOrderGuidController {

    Logger mLogger = LoggerFactory.getLogger(SalesOrderGuidController.class);

    @Autowired
    SalesOrderGuidService mSalesOrderGuidService;

    @Autowired
    ValidateSalesOrderGuidService mValidateSalesOrderGuidService;

    /**
     * @param pRequest (HttpServletRequest)
     * @return (ResponseEntity < WinResponse >) -
     */
    @GetMapping(value = "GetSalesOrderGuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WinResponse> getSalesOrderGuid(HttpServletRequest pRequest, @RequestBody SalesOrderGuidRequest pSalesOrderGuidRequest) {

        mLogger.info("Getting the Guid for the Sales Order.: {}", pSalesOrderGuidRequest);

        ResponseEntity<WinResponse> lResponse;
        Map<String, Object> lWinResponseMeta = new HashMap<String, Object>();
        GuidMaster lGuidMasterRecord;

        String lErrorMsg = mValidateSalesOrderGuidService.ValidateSalesOrderGuidRequest(pSalesOrderGuidRequest);
        if (lErrorMsg != null) {
            mLogger.info("Unable to get sales order GUID.  {} Invalid user request for SalesOrderGuid {}", lErrorMsg, pSalesOrderGuidRequest);
            lWinResponseMeta.put("msg", "Invalid User Request: " + lErrorMsg + pSalesOrderGuidRequest);
            lWinResponseMeta.put("success", "false");
            lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
        } else {
            try {
                lGuidMasterRecord =
                    mSalesOrderGuidService.getSalesOrderGuidByCompanyNumberAndByCustomerTypeAndByCustomerAccountAndByObjectTypeAndByOrderNumber(pSalesOrderGuidRequest);
                if (lGuidMasterRecord != null) {
                    lWinResponseMeta.put("success", "true");
                    lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, lGuidMasterRecord), HttpStatus.OK);
                } else {
                    mLogger.info("Unable to get sales order GUID.  Invalid user request for SalesOrderGuid {}", pSalesOrderGuidRequest);
                    lWinResponseMeta.put("msg", "Sales Order GUID does not exist: " + pSalesOrderGuidRequest);
                    lWinResponseMeta.put("success", "false");
                    lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
                }
            } catch (final Exception lException) {
                mLogger.info("Unable to get sales order GUID.  Exception getting SalesOrderGuid {}", pSalesOrderGuidRequest, lException);
                lWinResponseMeta.put("msg", "Exception getting Sales Order GUID: " + pSalesOrderGuidRequest + " Exception - " + lException);
                lWinResponseMeta.put("success", "false");
                lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return lResponse;
    }

    /**
     * @param pRequest (HttpServletRequest)
     * @return (ResponseEntity < WinResponse >) -
     */
    @PostMapping(value = "CreateSalesOrderGuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WinResponse> createSalesOrderGuid(HttpServletRequest pRequest, @RequestBody SalesOrderGuidRequest pSalesOrderGuidRequest) {

        mLogger.info("Creating Guid for Sales Order.: {}", pSalesOrderGuidRequest);

        ResponseEntity<WinResponse> lResponse = null;
        Map<String, Object> lWinResponseMeta = new HashMap<String, Object>();

        GuidMaster lGuidMasterRecord = null;

        String lErrorMsg = mValidateSalesOrderGuidService.ValidateSalesOrderGuidRequest(pSalesOrderGuidRequest);
        if (lErrorMsg != null) {
            mLogger.info("Unable to create sales order GUID.  {} Invalid user request for CreateSalesOrderGuid {}", lErrorMsg, pSalesOrderGuidRequest);
            lWinResponseMeta.put("msg", "Invalid User Request: " + lErrorMsg + pSalesOrderGuidRequest);
            lWinResponseMeta.put("success", "false");
            lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
        } else {

            try {
                lGuidMasterRecord =
                        mSalesOrderGuidService.getSalesOrderGuidByCompanyNumberAndByCustomerTypeAndByCustomerAccountAndByObjectTypeAndByOrderNumber(pSalesOrderGuidRequest);
                if (lGuidMasterRecord == null) {
                    lGuidMasterRecord = mSalesOrderGuidService.CreateSalesOrderGuid(pSalesOrderGuidRequest);
                    if (lGuidMasterRecord != null) {
                        lWinResponseMeta.put("success", "true");
                        lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, lGuidMasterRecord), HttpStatus.OK);
                    } else {
                        mLogger.info("Unable to create sales order GUID.  Request for SalesOrderGuid {}", pSalesOrderGuidRequest);
                        lWinResponseMeta.put("msg", "Sales Order GUID did not create: " + pSalesOrderGuidRequest);
                        lWinResponseMeta.put("success", "false");
                        lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    mLogger.info("Unable to create sales order GUID because it exists.  Request for SalesOrderGuid {}", pSalesOrderGuidRequest);
                    lWinResponseMeta.put("msg", "Sales Order GUID did not create because it exists: " + pSalesOrderGuidRequest);
                    lWinResponseMeta.put("success", "false");
                    lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
                }

            } catch (final Exception lException) {
                mLogger.info("Error creating sales order GUID.  Request for SalesOrderGuid {}", pSalesOrderGuidRequest, lException);
                lWinResponseMeta.put("msg", "Error Creating Sales Order GUID: " + pSalesOrderGuidRequest + " Exception - " + lException);
                lWinResponseMeta.put("success", "false");
                lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return lResponse;
    }

    /**
     * @param pRequest (HttpServletRequest)
     * @return (ResponseEntity < WinResponse >) -
     */
    @DeleteMapping(value = "DeleteSalesOrderGuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WinResponse> deleteSalesOrderGuid(HttpServletRequest pRequest, @RequestBody SalesOrderGuidRequest pSalesOrderGuidRequest) {

        mLogger.info("Deleting Guid for Sales Order.: {}", pSalesOrderGuidRequest);

        ResponseEntity<WinResponse> lResponse = null;
        Map<String, Object> lWinResponseMeta = new HashMap<String, Object>();

        GuidMaster lGuidMasterRecord = null;

        String lErrorMsg = mValidateSalesOrderGuidService.ValidateSalesOrderGuidRequest(pSalesOrderGuidRequest);
        if (lErrorMsg != null) {
            mLogger.info("Invalid user header value/request body for deleteSalesOrderGuid {} Request {} ", lErrorMsg, pSalesOrderGuidRequest);
            lWinResponseMeta.put("msg", "Header value/Request body is missing: " + lErrorMsg + pSalesOrderGuidRequest);
            lWinResponseMeta.put("success", "false");
            lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
        } else {
            try {
                lGuidMasterRecord = mSalesOrderGuidService.getSalesOrderGuidByCompanyNumberAndByCustomerTypeAndByCustomerAccountAndByObjectTypeAndByOrderNumber(
                        pSalesOrderGuidRequest);
                if (lGuidMasterRecord != null && mSalesOrderGuidService.DeleteSalesOrderGuid(pSalesOrderGuidRequest, lGuidMasterRecord) == true) {
                    lWinResponseMeta.put("success", "true");
                    lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, lGuidMasterRecord), HttpStatus.OK);
                } else {
                    mLogger.info("Unable to delete sales order GUID.  Request for SalesOrderGuid {}", pSalesOrderGuidRequest);
                    lWinResponseMeta.put("msg", "Sales Order GUID did not delete: " + pSalesOrderGuidRequest);
                    lWinResponseMeta.put("success", "false");
                    lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
                }
            } catch (final Exception lException) {
                mLogger.info("Error deleting sales order GUID.  Request for SalesOrderGuid {}", pSalesOrderGuidRequest, lException);
                lWinResponseMeta.put("msg", "Error Deleting Sales Order GUID: " + pSalesOrderGuidRequest + " Exception - " + lException);
                lWinResponseMeta.put("success", "false");
                lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return lResponse;
    }

    /**
     * @param pRequest (HttpServletRequest)
     * @return (ResponseEntity < WinResponse >) -
     */
    @PutMapping(value = "UpdateSalesOrderGuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WinResponse> updateSalesOrderGuid(HttpServletRequest pRequest, @RequestBody SalesOrderGuidUpdateRequest pSalesOrderGuidUpdateRequest) throws Exception {

        mLogger.info("Updating Guid for Sales Order.: {}", pSalesOrderGuidUpdateRequest);

        ResponseEntity<WinResponse> lResponse = null;
        Map<String, Object> lWinResponseMeta = new HashMap<String, Object>();

        String lErrorMsg = mValidateSalesOrderGuidService.ValidateSalesOrderGuidUpdateRequest(pSalesOrderGuidUpdateRequest);
        if (lErrorMsg != null) {
            mLogger.info("Invalid user header value/request body for updateSalesOrderGuid {} {} ", lErrorMsg, pSalesOrderGuidUpdateRequest);
            lWinResponseMeta.put("msg", "Header value/Request body is missing: " + lErrorMsg + pSalesOrderGuidUpdateRequest);
            lWinResponseMeta.put("success", "false");
            lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
        } else {
            GuidMaster lGuidMasterRecord = null;
            String lGuidMasterId = pSalesOrderGuidUpdateRequest.getGuidMasterId();
            lGuidMasterRecord = mSalesOrderGuidService.getSalesOrderGuidByGuidMasterId(lGuidMasterId);
            if (lGuidMasterRecord == null) {
                mLogger.info("Invalid user header value/request body for updateSalesOrderGuid {}", pSalesOrderGuidUpdateRequest);
                lWinResponseMeta.put("msg", "Header value/Request body is missing: " + pSalesOrderGuidUpdateRequest);
                lWinResponseMeta.put("success", "false");
                lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
            } else {
                try {
                    if (mSalesOrderGuidService.UpdateSalesOrderGuid(pSalesOrderGuidUpdateRequest, lGuidMasterRecord)) {
                        lWinResponseMeta.put("success", "true");
                        lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, lGuidMasterRecord), HttpStatus.OK);
                    } else {
                        mLogger.info("Unable to update sales order GUID.  Request for SalesOrderGuid {}", pSalesOrderGuidUpdateRequest);
                        lWinResponseMeta.put("msg", "Sales Order GUID did not update: " + pSalesOrderGuidUpdateRequest);
                        lWinResponseMeta.put("success", "false");
                        lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.BAD_REQUEST);
                    }
                } catch (final Exception lException) {
                    mLogger.info("Error updating sales order GUID.  Request for SalesOrderGuid {}", pSalesOrderGuidUpdateRequest, lException);
                    lWinResponseMeta.put("msg", "Error Updating Sales Order GUID: " + pSalesOrderGuidUpdateRequest + " Exception - " + lException);
                    lWinResponseMeta.put("success", "false");
                    lResponse = new ResponseEntity<WinResponse>(WinResponse.fromMetaAndData(lWinResponseMeta, null), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return lResponse;
    }
}

