package com.htp.repository.spingdata;

import com.htp.controller.requests.StockMaterialsRequests;
import com.htp.domain.hibernate.HibernateShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface SpringDataShipmentDao extends JpaRepository<HibernateShipment, Long> {

    List<HibernateShipment> findByShipmentDateBetweenOrderByShipmentDate(Date date1, Date date2);

    List<HibernateShipment> findByShipmentProductAndShipmentDateBetween(String productName, Date date1, Date date2);

    List<HibernateShipment> findByShipmentToCompanyAndShipmentDateBetween(String companyName, Date date1, Date date2);

    @Query(value = "select t1.a1 as product_name, t1.a2 as product_unit, sum(t1.a3) as shipment_quantity, t1.a4 as shipment_price " +
            "FROM " +
            "(SELECT product_catalog.product_name as a1, product_catalog.product_unit as a2,  sum(receipt.receipt_quantity) as a3, receipt.receipt_price as a4 " +
            "FROM receipt " +
            "Inner JOIN product_catalog ON  product_catalog.product_id = receipt.product_catalog_id " +
            "GROUP BY product_catalog.product_id, receipt.receipt_price  " +
            "UNION " +
            "SELECT product_catalog.product_name, product_catalog.product_unit, -sum(shipment.shipment_quantity), shipment.shipment_price  " +
            "FROM shipment " +
            "Inner JOIN product_catalog ON  product_catalog.product_id = shipment.product_catalog_id " +
            "GROUP BY product_catalog.product_id) as t1 " +
            "GROUP BY t1.a1, t1.a2, t1.a4", nativeQuery = true)
    List<StockMaterialsRequests> resultForNow();
}
