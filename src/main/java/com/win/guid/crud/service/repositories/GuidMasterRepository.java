package com.win.guid.crud.service.repositories;

import com.win.guid.crud.service.models.GuidMaster;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Purpose: Guid Crud
 * Programmer:  Tim Lane
 * Date: 01/18/23
 * Guid Master Table (ACGUIDMT) - only used for CRUD operations
 * MVP
 */

@Repository
public interface GuidMasterRepository extends JpaRepository<GuidMaster, Long> {

    boolean existsGuidMasterByGuid(String guid);

   // used to find the guid for a sales order
   GuidMaster
   findFirstByCompanyNumberAndCustomerTypeAndCustomerAccountAndObjectTypeAndOrderNumberOrderByAddedTimestampDesc
           (String companyNumber,
            String customerType,
            String customerAccount,
            String objectType,
            String orderNumber);

   GuidMaster
   findFirstByGuidMasterId(Long guidMasterId);

   /* @Query(value = "Select l from GuidMaster l " +
    " WHERE l.companyNumber = ?1 AND " +
            " l.customerType = ?2 And " +
            " l.customerAccount = ?3 And " +
            " l.objectType = ?4 And " +
            " l.orderNumber = ?5 " +
            " Order By l.addedTimestamp desc ")
   GuidMaster findSalesOrderGuidByCustomQuery(String pCompanyNumber,
                                               String pCustomerType,
                                               String pCustomerAccount,
                                               String pObjectType,
                                               String pOrderNumber);  */

}
