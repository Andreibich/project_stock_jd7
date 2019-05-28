package com.htp.repository.hibernate;

import com.htp.domain.hibernate.HibernateOperationCodes;
import com.htp.repository.GenericDao;

public interface HibernateOperationCodesDao extends GenericDao<HibernateOperationCodes, Long> {

    HibernateOperationCodes findByPurpose(String purpose);
}
