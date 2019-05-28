package com.htp.controller.springdata;

import com.htp.controller.requests.ReceiptCreateRequest;
import com.htp.domain.hibernate.HibernateReceipt;
import com.htp.repository.spingdata.SpringDataCompaniesDao;
import com.htp.repository.spingdata.SpringDataProductCatalogDao;
import com.htp.repository.spingdata.SpringDataReceiptDao;
import com.htp.repository.spingdata.SpringDataUserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Api(value = "Account", description = "APIs for working with users")
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/receipt")
public class SpringdataReceiptController {


    @Autowired
    private SpringDataReceiptDao springDataReceiptDao;

    @GetMapping("/receiptsByPeriod")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateReceipt>> getReceiptsByPeriod(Date date1, Date date2) {

        return new ResponseEntity<>(springDataReceiptDao.findByReceiptDateBetweenOrderByReceiptDate(date1, date2), HttpStatus.OK);
    }

    @GetMapping("/product_date")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateReceipt>> getReceiptsByProductAndPeriod(String product, Date date1, Date date2) {
        return new ResponseEntity<>(springDataReceiptDao.findByReceiptProductAndReceiptDateBetween(product, date1, date2), HttpStatus.OK);
    }

    @GetMapping("/company_date")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateReceipt>> getReceiptsByCompanyAndPeriod(String company, Date date1, Date date2) {
        return new ResponseEntity<>(springDataReceiptDao.findByReceiptFromCompanyAndReceiptDateBetween(company, date1, date2), HttpStatus.OK);
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateReceipt>> getAll() {

        return new ResponseEntity<>(springDataReceiptDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<HibernateReceipt>> getProductById(@ApiParam("HibernateReceipt Path Id") @PathVariable Long id) {
        Optional<HibernateReceipt> receipt = springDataReceiptDao.findById(id);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }


    @Autowired
    private SpringDataUserDao springDataUserDao;

    @Autowired
    private SpringDataProductCatalogDao springDataProductCatalogDao;

    @Autowired
    private SpringDataCompaniesDao springDataCompaniesDao;


    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateReceipt> createReceipt(@RequestBody ReceiptCreateRequest request) {

        var receipt = new HibernateReceipt();

        receipt.setReceiptUserId(springDataUserDao.findBySurname(request.getUserSurname()));
        receipt.setInvoiceNumber(request.getInvoiceNumber());
        receipt.setReceiptPrice(request.getReceiptPrice());
        receipt.setReceiptQuantity(request.getReceiptQuantity());
        receipt.setReceiptProduct(springDataProductCatalogDao.findByProductName(request.getProductCatalogName()));
        receipt.setReceiptFromCompany(springDataCompaniesDao.findByCompanyName(request.getSupplier()));

        HibernateReceipt savedReceipt = springDataReceiptDao.save(receipt);

        return new ResponseEntity<>(savedReceipt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteReceipt(@PathVariable("id") Long receiptId) {
        springDataReceiptDao.deleteById(receiptId);
        return new ResponseEntity<>(receiptId, HttpStatus.OK);
    }


}
