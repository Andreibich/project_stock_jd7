package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateOperationCodes;
import com.htp.domain.hibernate.HibernateProductCatalog;
import com.htp.repository.hibernate.HibernateOperationCodesDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Qualifier("hibernateOperationCodesDaoImpl")
public class HibernateOperationCodesDaoImpl implements HibernateOperationCodesDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateOperationCodes> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hoc from HibernateOperationCodes hoc", HibernateOperationCodes.class).getResultList();
        }
    }

    @Override
    public HibernateOperationCodes findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibernateOperationCodes save(HibernateOperationCodes entity) {
        return null;
    }

    @Override
    public HibernateOperationCodes update(HibernateOperationCodes entity) {
        return null;
    }
}
