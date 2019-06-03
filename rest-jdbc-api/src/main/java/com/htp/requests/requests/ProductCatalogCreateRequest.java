package com.htp.requests.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductCatalogCreateRequest {


    @NotNull
    private String productName;

    @NotNull
    private String productUnit;

}
