package com.htp.repository.jdbc;


import com.htp.requests.requests.ReceiptResultRequest;
import com.htp.domain.jdbc.Receipt;
import com.htp.repository.GenericDao;

import java.util.List;

public interface ReceiptDao extends GenericDao<Receipt, Long> {

    List<ReceiptResultRequest> findAllReceipts();

//    List<HibernateReceipt> findByPeriod(Date dateBeginning, Date dateEnd);
//
//    List<HibernateReceipt> findByProductNameAndPeriod(String productName, Date dateBeginning, Date dateEnd);
//
//    List<HibernateReceipt> findByCompanyNameAndPeriod(String companyName, Date dateBeginning, Date dateEnd);

}
