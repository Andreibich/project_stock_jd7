package com.htp.controller.requests;

import lombok.Data;

@Data
public class StockMaterialsRequests {

    private String productName;
    private String productUnit;
    private Double quantity;
    private Double price;

}
