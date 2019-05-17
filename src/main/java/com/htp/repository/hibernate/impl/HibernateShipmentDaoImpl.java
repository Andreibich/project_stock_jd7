package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateShipment;
import com.htp.domain.hibernate.HibernateUser;
import com.htp.repository.hibernate.HibernateShipmentDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Qualifier("hibernateShipmentDaoImpl")
public class HibernateShipmentDaoImpl implements HibernateShipmentDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateShipment> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hs from HibernateShipment hs", HibernateShipment.class).getResultList();
        }
    }

    @Override
    public HibernateShipment findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibernateShipment save(HibernateShipment entity) {
        return null;
    }

    @Override
    public HibernateShipment update(HibernateShipment entity) {
        return null;
    }
}
