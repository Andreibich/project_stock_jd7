package com.htp.requests.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompaniesCreateRequest {

    @NotNull
    private String companyName;

    @NotNull
    private String city;

    @NotNull
    private String address;
}
