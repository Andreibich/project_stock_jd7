package com.htp.requests.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String roleName;

}
