package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductCatalogDao extends JpaRepository<HibernateProductCatalog, Long> {

    HibernateProductCatalog findByProductName(String product);
}
