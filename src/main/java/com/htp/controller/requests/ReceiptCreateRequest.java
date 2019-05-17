package com.htp.controller.requests;

import lombok.Data;

import java.sql.Date;

@Data
public class ReceiptCreateRequest {

    private String supplier;
    private String productCatalogName;
    private Double receiptQuantity;
    private Double receiptPrice;
    private String invoiceNumber;
    private String userSurname;
}
