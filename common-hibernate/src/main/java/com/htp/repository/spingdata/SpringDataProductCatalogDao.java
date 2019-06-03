package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SpringDataProductCatalogDao extends JpaRepository<HibernateProductCatalog, Long> {

    HibernateProductCatalog findByProductName(String product);
}
