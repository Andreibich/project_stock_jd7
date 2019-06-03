package com.htp.requests.requests;


import lombok.Data;

@Data
public class SearchCriteria {
    private String query;
    private Integer limit;
    private Integer offset;

}
