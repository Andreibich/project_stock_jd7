package com.htp.requests.hibernate;

import com.htp.requests.requests.ReceiptCreateRequest;
import com.htp.domain.hibernate.HibernateReceipt;
import com.htp.repository.hibernate.HibernateCompaniesDao;
import com.htp.repository.hibernate.HibernateProductCatalogDao;
import com.htp.repository.hibernate.HibernateReceiptDao;
import com.htp.repository.hibernate.HibernateUserDao;
import io.swagger.annotations.ApiParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/receipt")
public class HibernateReceiptController {


    @Autowired
    private HibernateReceiptDao hibernateReceiptDaoImpl;

    @GetMapping("/all_hibernate_receipts")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateReceipt>> getReceiptsHibernate() {
        return new ResponseEntity<>(hibernateReceiptDaoImpl.findAll(), HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateReceipt> getHibernateReceiptById(@ApiParam("HibernateReceipt Path Id") @PathVariable Long id) {
        HibernateReceipt hibernateReceipt = hibernateReceiptDaoImpl.findById(id);
        return new ResponseEntity<>(hibernateReceipt, HttpStatus.OK);
    }

    @Autowired
    private HibernateUserDao hibernateUserDaoImpl;

    @Autowired
    private HibernateProductCatalogDao hibernateProductCatalogDaoImpl;

    @Autowired
    private HibernateCompaniesDao hibernateCompaniesDaoImpl;


    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateReceipt> createReceipt(@RequestBody ReceiptCreateRequest request) {

        var receipt = new HibernateReceipt();

        receipt.setReceiptUser(hibernateUserDaoImpl.findBySurname(request.getUserSurname()));
        receipt.setInvoiceNumber(request.getInvoiceNumber());
        receipt.setReceiptPrice(request.getReceiptPrice());
        receipt.setReceiptQuantity(request.getReceiptQuantity());
        receipt.setReceiptProduct(hibernateProductCatalogDaoImpl.findByProductName(request.getProductCatalogName()));
        receipt.setReceiptFromCompany(hibernateCompaniesDaoImpl.findByCompanyName(request.getSupplier()));

        HibernateReceipt savedReceipt = hibernateReceiptDaoImpl.save(receipt);

        return new ResponseEntity<>(savedReceipt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteHibernateReceipt(@PathVariable("id") Long receiptId) {
        hibernateReceiptDaoImpl.delete(receiptId);
        return new ResponseEntity<>(receiptId, HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateReceipt> updateHibernateReceipt(@RequestBody HibernateReceipt hibernateReceipt) {
        HibernateReceipt savedReceipt = hibernateReceiptDaoImpl.update(hibernateReceipt);
        return new ResponseEntity<>(savedReceipt, HttpStatus.OK);
    }
}
