package com.htp.repository.hibernate.impl;

import com.htp.domain.hibernate.HibernateCompanies;
import com.htp.domain.hibernate.HibernateCompanies_;
import com.htp.repository.hibernate.HibernateCompaniesDao;
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
@Qualifier("hibernateCompaniesDaoImpl")
public class HibernateCompaniesDaoImpl implements HibernateCompaniesDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibernateCompanies> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hc from HibernateCompanies hc", HibernateCompanies.class).getResultList();
        }
    }

    @Override
    public HibernateCompanies findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibernateCompanies.class, id);
        }
    }

    @Override
    public HibernateCompanies findByCompanyName(String companyName) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibernateCompanies> query = cb.createQuery(HibernateCompanies.class);
        Root<HibernateCompanies> root = query.from(HibernateCompanies.class);
        query.select(root)
                .distinct(true)
                .where(
                        cb.equal(root.get(HibernateCompanies_.companyName), companyName));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibernateCompanies> resultQuery = session.createQuery(query);

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
    public HibernateCompanies save(HibernateCompanies entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newHibernateCompaniesID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibernateCompanies.class, newHibernateCompaniesID);
        }
    }

    @Override
    public HibernateCompanies update(HibernateCompanies entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibernateCompanies.class, entity.getCompanyId());
        }
    }
}
