package com.htp.repository.jdbc;


import com.htp.domain.jdbc.ProductCatalog;
import com.htp.repository.GenericDao;

public interface ProductCatalogDao extends GenericDao<ProductCatalog, Long> {

    Long findByProductName(String productName);

}
