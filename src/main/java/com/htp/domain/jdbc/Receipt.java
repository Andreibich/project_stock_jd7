package com.htp.domain.jdbc;

import lombok.Data;

import java.sql.Date;

@Data
public class Receipt {


    private Long receiptId;
    private Date receiptDate;
    private Long supplierId;
    private Long productCatalogId;
    private Double receiptQuantity;
    private Double receiptPrice;
    private String invoiceNumber;
    private Long receiptUserId;


}
