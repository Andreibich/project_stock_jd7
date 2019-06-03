package com.htp.repository.jdbc;

import com.htp.requests.requests.StockMaterialsRequests;
import com.htp.domain.jdbc.Shipment;
import com.htp.repository.GenericDao;

import java.util.List;

public interface ShipmentDao extends GenericDao<Shipment, Long> {

//    List<HibernateShipment> findByPeriod(Date dateBeginning, Date dateEnd);
//
//    List<HibernateShipment> findByProductNameAndPeriod(String productName, Date dateBeginning, Date dateEnd);
//
//    List<HibernateShipment> findByCompanyNameAndPeriod(String companyName, Date dateBeginning, Date dateEnd);
//
    List<StockMaterialsRequests> findResult();
//
//    List<HibernateShipment> findResultDate(Date date);



}
