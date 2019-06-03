package com.htp.requests.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ReceiptCreateRequest {

    @NotNull
    private String supplier;

    @NotNull
    private String productCatalogName;

    @NotNull
    @Pattern(regexp = "/^[0-9]+(\\\\.[0-9]+)?$")
    private Double receiptQuantity;

    @NotNull
    @Pattern(regexp = "/^[0-9]+(\\\\.[0-9]+)?$")
    private Double receiptPrice;

    @NotNull
    @Pattern(regexp = "[0-9]{1,11}")
    private String invoiceNumber;

    @NotNull
    private String userSurname;
}
