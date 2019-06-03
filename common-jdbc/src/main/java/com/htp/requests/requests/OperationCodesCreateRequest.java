package com.htp.requests.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OperationCodesCreateRequest {

    @NotNull
    private String purpose;
}
