package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateCompanies;
import com.htp.repository.hibernate.HibernateCompaniesDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateCompanies.class, id);
        }
    }

    @Override
    public HibernateCompanies findByCompanyName(String companyName) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateCompanies.class, companyName);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibernateCompanies save(HibernateCompanies entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newHibernateCompaniesID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateCompanies.class, newHibernateCompaniesID);
        }
    }

    @Override
    public HibernateCompanies update(HibernateCompanies entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateCompanies.class, entity.getCompanyId());
        }
    }
}
