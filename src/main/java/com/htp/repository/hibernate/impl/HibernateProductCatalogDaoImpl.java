package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateProductCatalog;
import com.htp.domain.hibernate.HibernateUser;
import com.htp.repository.hibernate.HibernateProductCatalogDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibernateProductCatalog save(HibernateProductCatalog entity) {
        return null;
    }

    @Override
    public HibernateProductCatalog update(HibernateProductCatalog entity) {
        return null;
    }
}
