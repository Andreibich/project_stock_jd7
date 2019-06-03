package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

public interface SpringDataReceiptDao extends JpaRepository<HibernateReceipt, Long> {

    List<HibernateReceipt> findByReceiptDateBetweenOrderByReceiptDate(Date date1, Date date2);

//    @Query("SELECT r FROM HibernateReceipt r  WHERE HibernateProductCatalog.productName = :productName AND " +
//            "r.receiptDate >= :date1 AND r.receiptDate <= :date2")
//    List<HibernateReceipt> findByProductNameAndPeriod(@Param("productName") String productName, @Param("date1") Date date1,
//                                                      @Param("date2") Date date2);

    List<HibernateReceipt> findByReceiptProductAndReceiptDateBetween(String productName, Date date1, Date date2);

    List<HibernateReceipt> findByReceiptFromCompanyAndReceiptDateBetween(String companyName, Date date1, Date date2);

}
