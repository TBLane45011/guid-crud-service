package com.win.guid.crud.service.services;

import com.win.guid.crud.service.models.SalesOrderGuidRequest;
import com.win.guid.crud.service.models.SalesOrderGuidUpdateRequest;
import com.win.guid.crud.service.repositories.GuidMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ValidateSalesOrderGuidService {

    @Autowired
    GuidMasterRepository mGuidMasterRepository;

    public String ValidateSalesOrderGuidRequest(final SalesOrderGuidRequest pSalesOrderGuidRequest) {

        String lErrorMsg = null;
        String lCompanyNumber = pSalesOrderGuidRequest.getCompanyNumber();
        String lCustomerType = pSalesOrderGuidRequest.getCustomerType();
        String lCustomerAccount = pSalesOrderGuidRequest.getCustomerAccount();
        String lObjectType = pSalesOrderGuidRequest.getObjectType();
        String lOrderNumber = pSalesOrderGuidRequest.getOrderNumber();

        lErrorMsg = ValidateCompanyNumber(lCompanyNumber);
        lErrorMsg += ValidateCustomerType(lCustomerType);
        lErrorMsg += ValidateCustomerAccount(lCustomerAccount);
        lErrorMsg += ValidateObjectType(lObjectType);
        lErrorMsg += ValidateOrderNumber(lOrderNumber);

        if (lErrorMsg.equals("")) {
            lErrorMsg = null;
        }

        return lErrorMsg;

    }

    public String ValidateSalesOrderGuidUpdateRequest(final SalesOrderGuidUpdateRequest pSalesOrderGuidUpdateRequest) {

        String lErrorMsg = null;
        String lGuidMasterId = pSalesOrderGuidUpdateRequest.getGuidMasterId();
        String lCompanyNumber = pSalesOrderGuidUpdateRequest.getCompanyNumber();
        String lCustomerType = pSalesOrderGuidUpdateRequest.getCustomerType();
        String lCustomerAccount = pSalesOrderGuidUpdateRequest.getCustomerAccount();
        String lObjectType = pSalesOrderGuidUpdateRequest.getObjectType();
        String lOrderNumber = pSalesOrderGuidUpdateRequest.getOrderNumber();

        lErrorMsg = ValidateGuidMasterId(lGuidMasterId);
        lErrorMsg += ValidateCompanyNumber(lCompanyNumber);
        lErrorMsg += ValidateCustomerType(lCustomerType);
        lErrorMsg += ValidateCustomerAccount(lCustomerAccount);
        lErrorMsg += ValidateObjectType(lObjectType);
        lErrorMsg += ValidateOrderNumber(lOrderNumber);

        if (lErrorMsg.equals("")) {
            lErrorMsg = null;
        }

        return lErrorMsg;

    }

            public String ValidateCompanyNumber(String pCompanyNumber) {

                String lErrorMsg = "";
                if (!StringUtils.hasText(pCompanyNumber)) {
                    lErrorMsg = "Error Company Number must have a value. ";
                }

                // might want to verify that the company number is valid against the database later

                return lErrorMsg;

            }

            public String ValidateCustomerType (String pCustomerType){

                String lErrorMsg = "";
                if (!StringUtils.hasText(pCustomerType)) {
                    lErrorMsg = "Error Customer Type must have a value. ";
                }

                // might want to verify that the customer type is valid against the database later

                return lErrorMsg;

            }

            public String ValidateCustomerAccount (String pCustomerAccount){

                String lErrorMsg = "";
                if (!StringUtils.hasText(pCustomerAccount)) {
                    lErrorMsg = "Error Customer Account must have a value. ";
                }

                // might want to verify that the customer account is valid against the database later

                return lErrorMsg;

            }

            public String ValidateObjectType (String pObjectType){

                String lErrorMsg = "";
                if (!StringUtils.hasText(pObjectType)) {
                    lErrorMsg = "Error Object Type must have a value. ";
                }

                // might want to verify that the object type is valid against the database later

                return lErrorMsg;

            }

            public String ValidateOrderNumber (String pOrderNumber){

                String lErrorMsg = "";
                if (!StringUtils.hasText(pOrderNumber)) {
                    lErrorMsg = "Error Order Number must have a value. ";
                }

                // might want to verify that the order number is valid against the database later

                return lErrorMsg;

            }

            public String ValidateGuidMasterId (String pGuidMasterId) {

                String lErrorMsg = "";
                if (!StringUtils.hasText(pGuidMasterId)) {
                lErrorMsg = "Error Guid Master must have a value. ";
                }

                // might want to verify that the Guid Master is valid against the database later

                return lErrorMsg;

            }

        }
