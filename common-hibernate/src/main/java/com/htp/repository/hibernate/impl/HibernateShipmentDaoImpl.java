package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateShipment;
import com.htp.domain.hibernate.HibernateShipment_;
import com.htp.repository.hibernate.HibernateShipmentDao;
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
//        try (Session session = sessionFactory.openSession()) {
//            return session.find(HibernateShipment.class, id);
//        }
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibernateShipment> query = cb.createQuery(HibernateShipment.class);
        Root<HibernateShipment> root = query.from(HibernateShipment.class);
        query.select(root)
                .distinct(true)
                .where(
                        cb.equal(root.get(HibernateShipment_.shipmentId), id));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibernateShipment> resultQuery = session.createQuery(query);

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
    public HibernateShipment save(HibernateShipment entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            entity.setShipmentDate(new Date(new Timestamp(System.currentTimeMillis()).getTime()));
            Long newHibernateShipmentID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateShipment.class, newHibernateShipmentID);
        }
    }

    @Override
    public HibernateShipment update(HibernateShipment entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            entity.setShipmentDate(new Date(new Timestamp(System.currentTimeMillis()).getTime()));
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateShipment.class, entity.getShipmentId());
        }
    }

    @Override
    public List<HibernateShipment> findByPeriod(Date dateBeginning, Date dateEnd) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibernateShipment> query = cb.createQuery(HibernateShipment.class);
        Root<HibernateShipment> root = query.from(HibernateShipment.class);
        query.select(root)
                .distinct(true)
                .where(
                        cb.between(root.get(HibernateShipment_.shipmentDate), dateBeginning, dateEnd));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibernateShipment> resultQuery = session.createQuery(query);
            return resultQuery.getResultList();
        }
    }

    @Override
    public List<HibernateShipment> findByProductAndPeriod(Long productId, Date dateBeginning, Date dateEnd) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibernateShipment> query = cb.createQuery(HibernateShipment.class);
        Root<HibernateShipment> root = query.from(HibernateShipment.class);
        query.select(root)
                .distinct(true)
                .where(
                        cb.between(root.get(HibernateShipment_.shipmentDate), dateBeginning, dateEnd),
                        cb.equal(root.get(HibernateShipment_.shipmentProduct), productId));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibernateShipment> resultQuery = session.createQuery(query);
            return resultQuery.getResultList();
        }
    }

    @Override
    public List<HibernateShipment> findByCompanyAndPeriod(Long companyId, Date dateBeginning, Date dateEnd) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibernateShipment> query = cb.createQuery(HibernateShipment.class);
        Root<HibernateShipment> root = query.from(HibernateShipment.class);
        query.select(root)
                .distinct(true)
                .where(
                        cb.equal(root.get(HibernateShipment_.shipmentToCompany), companyId),
                        cb.between(root.get(HibernateShipment_.shipmentDate), dateBeginning, dateEnd)
                       );

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibernateShipment> resultQuery = session.createQuery(query);
            return resultQuery.getResultList();
        }
    }
}