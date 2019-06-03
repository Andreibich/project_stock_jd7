package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateCompanies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SpringDataCompaniesDao extends JpaRepository<HibernateCompanies, Long> {

    List<HibernateCompanies> findByCompanyNameStartingWithOrderByCompanyNameAsc(String query);

    HibernateCompanies findByCompanyName(String name);

}
