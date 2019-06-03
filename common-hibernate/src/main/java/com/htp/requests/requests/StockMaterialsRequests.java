package com.htp.requests.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StockMaterialsRequests {

    @NotNull
    private String productName;

    @NotNull
    private String productUnit;

    @NotNull
    private Double quantity;

    @NotNull
    private Double price;

}
