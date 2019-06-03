package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateReceipt;
import com.htp.repository.hibernate.HibernateReceiptDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
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
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateReceipt.class, id);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibernateReceipt save(HibernateReceipt entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            entity.setReceiptDate(new Date(new Timestamp(System.currentTimeMillis()).getTime()));
            Long newHibernateReceiptID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateReceipt.class, newHibernateReceiptID);
        }
    }

    @Override
    public HibernateReceipt update(HibernateReceipt entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateReceipt.class, entity.getReceiptId());
        }
    }
}
