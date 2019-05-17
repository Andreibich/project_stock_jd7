package com.htp.controller.requests;


import lombok.Data;

@Data
public class SearchCriteria {
    private String query;
    private Integer limit;
    private Integer offset;

}
