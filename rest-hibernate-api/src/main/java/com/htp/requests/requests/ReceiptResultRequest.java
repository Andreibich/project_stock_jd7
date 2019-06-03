package com.htp.requests.requests;

import lombok.Data;

import java.sql.Date;

@Data
public class ReceiptResultRequest {

    private Long receiptId;
    private Date receiptDate;
    private Double receiptQuantity;
    private Double receiptPrice;
    private String invoiceNumber;


    private String supplierName;
    private String productCatalogName;
    private String productCatalogUnit;
    private String receiptUserSurname;
}
