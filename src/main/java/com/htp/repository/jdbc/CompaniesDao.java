package com.htp.repository.jdbc;


import com.htp.domain.jdbc.Companies;
import com.htp.repository.GenericDao;

public interface CompaniesDao extends GenericDao<Companies, Long> {

    Long findByCompanyName(String companyName);

}
