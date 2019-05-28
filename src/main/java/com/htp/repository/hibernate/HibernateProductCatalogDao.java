package com.htp.repository.hibernate;

import com.htp.domain.hibernate.HibernateProductCatalog;
import com.htp.repository.GenericDao;

public interface HibernateProductCatalogDao extends GenericDao<HibernateProductCatalog, Long> {

    HibernateProductCatalog findByProductName(String productName);
}
