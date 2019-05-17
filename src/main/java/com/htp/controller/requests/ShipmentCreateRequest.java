package com.htp.controller.requests;

import lombok.Data;

import java.sql.Date;

@Data
public class ShipmentCreateRequest {

    private Double shipmentQuantity;
    private Double shipmentPrice;

    private String productCatalogName;
    private String shipmentUserSurname;
    private String operationCodePurpose;
}
