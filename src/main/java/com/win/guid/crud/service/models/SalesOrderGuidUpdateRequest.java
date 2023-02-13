package com.win.guid.crud.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderGuidUpdateRequest {

    private String guidMasterId;
    private String companyNumber;
    private String customerType;
    private String customerAccount;
    private String objectType;
    private String orderNumber;

}

