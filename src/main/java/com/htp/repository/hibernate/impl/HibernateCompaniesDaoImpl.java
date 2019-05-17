package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateCompanies;
import com.htp.domain.hibernate.HibernateOperationCodes;
import com.htp.repository.hibernate.HibernateCompaniesDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Qualifier("hibernateCompaniesDaoImpl")
public class HibernateCompaniesDaoImpl implements HibernateCompaniesDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateCompanies> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hc from HibernateCompanies hc", HibernateCompanies.class).getResultList();
        }
    }

    @Override
    public HibernateCompanies findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibernateCompanies save(HibernateCompanies entity) {
        return null;
    }

    @Override
    public HibernateCompanies update(HibernateCompanies entity) {
        return null;
    }
}
