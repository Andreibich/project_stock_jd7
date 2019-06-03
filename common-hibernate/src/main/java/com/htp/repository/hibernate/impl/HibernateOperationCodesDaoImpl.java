package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateOperationCodes;
import com.htp.domain.hibernate.HibernateOperationCodes_;
import com.htp.repository.hibernate.HibernateOperationCodesDao;
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
import java.util.List;


@Repository
@Qualifier("hibernateOperationCodesDaoImpl")
public class HibernateOperationCodesDaoImpl implements HibernateOperationCodesDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateOperationCodes> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hoc from HibernateOperationCodes hoc", HibernateOperationCodes.class).getResultList();
        }
    }

    @Override
    public HibernateOperationCodes findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateOperationCodes.class, id);
        }
    }

    @Override
    public HibernateOperationCodes findByPurpose(String purpose) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibernateOperationCodes> query = cb.createQuery(HibernateOperationCodes.class);
        Root<HibernateOperationCodes> root = query.from(HibernateOperationCodes.class);
        query.select(root)
                .distinct(true)
                .where(
                        cb.equal(root.get(HibernateOperationCodes_.purpose), purpose));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibernateOperationCodes> resultQuery = session.createQuery(query);

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
    public HibernateOperationCodes save(HibernateOperationCodes entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newHibernateOperationCodesID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateOperationCodes.class, newHibernateOperationCodesID);
        }
    }

    @Override
    public HibernateOperationCodes update(HibernateOperationCodes entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateOperationCodes.class, entity.getOperationCodeId());
        }
    }
}
