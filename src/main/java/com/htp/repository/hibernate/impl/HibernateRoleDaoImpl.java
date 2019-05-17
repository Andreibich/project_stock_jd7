package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateRole;
import com.htp.domain.hibernate.HibernateShipment;
import com.htp.repository.hibernate.HibernateRoleDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Qualifier("hibernateRoleDaoImpl")
public class HibernateRoleDaoImpl implements HibernateRoleDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateRole> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hr from HibernateRole hr", HibernateRole.class).getResultList();
        }
    }

    @Override
    public HibernateRole findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibernateRole save(HibernateRole entity) {
        return null;
    }

    @Override
    public HibernateRole update(HibernateRole entity) {
        return null;
    }
}
