package com.htp.repository.jdbc;


import com.htp.domain.jdbc.OperationCodes;
import com.htp.repository.GenericDao;

public interface OperationCodesDao extends GenericDao<OperationCodes, Long> {

    Long findByPurpose(String purpose);

}
