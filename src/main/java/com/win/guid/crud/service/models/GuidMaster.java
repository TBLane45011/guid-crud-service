package com.win.guid.crud.service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Purpose: Create Guid for sales orders, shipments, invoices, payments
 * Programmer:  Tim Lane
 * Date: 01/17/23
 * Guid Master Table - only used for CUD operations
 * MVP
 */

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "GuidMaster")
@Table(name = "ACGUIDMT")
public class GuidMaster {

    /** wise short name - ACGUIDMTID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acguidmt_id", insertable = false)
    private Long guidMasterId;

    /** wise short name - GUI */
    @Column(name = "guid", nullable = false)
    private String guid;

    /** wise short name - CONUM */
    @Column(name = "company_number", nullable = false)
    private String companyNumber;

    /** wise short name - OBJTYP */
    @Column(name = "object_type", nullable = false)
    private String objectType;

    /** wise short name - CUSTYP */
    @Column(name = "customer_type")
    private String customerType;

    /** wise short name - CUSACT */
    @Column(name = "customer_account")
    private String customerAccount;

    /** wise short name - ORDNUM */
    @Column(name = "order_number")
    private String orderNumber;

    /** wise short name - SHPNBR */
    @Column(name = "shipment_number")
    private long shipmentNumber;

    /** wise short name - PMTNBR */
    /** blind key */
    @Column(name = "payment_number")
    private long paymentNumber;

    /** wise short name - ADDBYUSR */
    @Column(name = "added_by_user")
    private String addedByUser;

    /** wise short name - ADDBYPGM */
    @Column(name = "added_by_program")
    private String addedByProgram;

    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss:SSSSSS", timezone = "America/New_York")
    @Column(name = "added_timestamp", insertable = false, updatable = false)
    private Date addedTimestamp;

    /** wise short name - CHGBYUSR */
    @Column(name = "changed_by_user")
    private String changedByUser;

    /** wise short name - CHGBYPGM */
    @Column(name = "changed_by_program")
    private String changedByProgram;

    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss:SSSSSS", timezone = "America/New_York")
    @Column(name = "changed_timestamp", insertable = false, updatable = false)
    private Date changedTimestamp;



}
