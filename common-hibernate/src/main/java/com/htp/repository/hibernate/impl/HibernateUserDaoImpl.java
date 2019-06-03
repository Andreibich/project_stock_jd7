package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateUser;
import com.htp.domain.hibernate.HibernateUser_;
import com.htp.repository.hibernate.HibernateUserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.sql.Timestamp;
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
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibernateUser> query = cb.createQuery(HibernateUser.class);
        Root<HibernateUser> root = query.from(HibernateUser.class);
        query.select(root)
                .distinct(true)
                .where(
                        cb.equal(root.get(HibernateUser_.surname), surname));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibernateUser> resultQuery = session.createQuery(query);

            return resultQuery.getSingleResult();
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
            entity.setCreationDate(new Date(new Timestamp(System.currentTimeMillis()).getTime()));
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
