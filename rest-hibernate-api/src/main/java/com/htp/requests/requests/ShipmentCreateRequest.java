package com.htp.requests.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ShipmentCreateRequest {

    @NotNull
    @Pattern(regexp = "/^[0-9]+(\\\\.[0-9]+)?$")
    private Double shipmentQuantity;

    @NotNull
    @Pattern(regexp = "/^[0-9]+(\\\\.[0-9]+)?$")
    private Double shipmentPrice;

    @NotNull
    private String productCatalogName;

    @NotNull
    private String shipmentUserSurname;

    @NotNull
    private String operationCodePurpose;
}
