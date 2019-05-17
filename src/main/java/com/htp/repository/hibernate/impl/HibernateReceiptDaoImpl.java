package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateReceipt;
import com.htp.repository.hibernate.HibernateReceiptDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("hibernateReceiptDaoImpl")
public class HibernateReceiptDaoImpl implements HibernateReceiptDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateReceipt> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hr from HibernateReceipt hr", HibernateReceipt.class).getResultList();
        }
    }

    @Override
    public HibernateReceipt findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibernateReceipt save(HibernateReceipt entity) {
        return null;
    }

    @Override
    public HibernateReceipt update(HibernateReceipt entity) {
        return null;
    }
}
