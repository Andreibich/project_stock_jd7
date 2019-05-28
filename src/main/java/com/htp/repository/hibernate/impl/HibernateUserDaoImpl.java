package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateUser;
import com.htp.repository.hibernate.HibernateUserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("hibernateUserDaoImpl")
public class HibernateUserDaoImpl implements HibernateUserDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateUser> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hu from HibernateUser hu", HibernateUser.class).getResultList();
        }
    }

    @Override
    public HibernateUser findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateUser.class, id);
        }
    }

    public HibernateUser findBySurname(String surname) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateUser.class, surname);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibernateUser save(HibernateUser entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newHibernateUserID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateUser.class, newHibernateUserID);
        }
    }

    @Override
    public HibernateUser update(HibernateUser entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateUser.class, entity.getUserId());
        }
    }
}
