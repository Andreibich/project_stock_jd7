package com.htp.domain.jdbc;


import lombok.Data;

import java.sql.Date;

@Data
public class Shipment {

    private Long shipmentId;
    private Date shipmentDate;
    private Long recipientCompanyId;
    private Long productCatalogId;
    private Double shipmentQuantity;
    private Double shipmentPrice;
    private String invoiceNumber;
    private Long proxyNumber;
    private Long shipmentUserId;
    private String recipientEmployeeName;
    private Long operationCodeId;

}
