package com.htp.repository.hibernate;

import com.htp.domain.hibernate.HibernateShipment;
import com.htp.repository.GenericDao;

import java.sql.Date;
import java.util.List;

public interface HibernateShipmentDao extends GenericDao<HibernateShipment, Long> {

    List<HibernateShipment> findByPeriod(Date dateBeginning, Date dateEnd);
}
