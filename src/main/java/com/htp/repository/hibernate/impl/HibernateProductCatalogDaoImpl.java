package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateProductCatalog;
import com.htp.repository.hibernate.HibernateProductCatalogDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("hibernateProductCatalogDaoImpl")
public class HibernateProductCatalogDaoImpl implements HibernateProductCatalogDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateProductCatalog> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hpc from HibernateProductCatalog hpc", HibernateProductCatalog.class).getResultList();
        }
    }

    @Override
    public HibernateProductCatalog findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateProductCatalog.class, id);
        }
    }

    @Override
    public HibernateProductCatalog findByProductName(String productName) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateProductCatalog.class, productName);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibernateProductCatalog save(HibernateProductCatalog entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newHibernateProductCatalogID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateProductCatalog.class, newHibernateProductCatalogID);
        }
    }

    @Override
    public HibernateProductCatalog update(HibernateProductCatalog entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateProductCatalog.class, entity.getProductId());
        }
    }
}
