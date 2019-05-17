package com.htp.domain.jdbc;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Data
public class User {

    private Long userId;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String phoneNumber;
    private String email;
    private Timestamp creationDate;
    private Long roleId;

}
