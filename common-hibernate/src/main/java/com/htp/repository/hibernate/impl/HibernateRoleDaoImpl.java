package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateRole;
import com.htp.repository.hibernate.HibernateRoleDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateRole.class, id);
        }
    }

    @Override
    public HibernateRole findByRoleName(String roleName) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateRole.class, roleName);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibernateRole save(HibernateRole entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newHibernateRoleID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateRole.class, newHibernateRoleID);
        }
    }

    @Override
    public HibernateRole update(HibernateRole entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateRole.class, entity.getRoleId());
        }
    }
}
