package com.htp.controller.requests;

import lombok.Data;

@Data
public class CompaniesCreateRequest {

    private String companyName;
    private String city;
    private String address;
}
