package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateUser;
import com.htp.repository.hibernate.HibernateUserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibernateUser save(HibernateUser entity) {
        return null;
    }

    @Override
    public HibernateUser update(HibernateUser entity) {
        return null;
    }
}
