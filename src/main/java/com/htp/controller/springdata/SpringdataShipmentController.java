package com.htp.controller.springdata;

import com.htp.controller.requests.ShipmentCreateRequest;
import com.htp.controller.requests.StockMaterialsRequests;
import com.htp.domain.hibernate.HibernateShipment;
import com.htp.domain.jdbc.Shipment;
import com.htp.repository.hibernate.HibernateShipmentDao;
import com.htp.repository.jdbc.OperationCodesDao;
import com.htp.repository.jdbc.ProductCatalogDao;
import com.htp.repository.jdbc.UserDao;
import com.htp.repository.spingdata.*;
import io.swagger.annotations.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Api(value = "Bccount", description = "APIs for working with shipments")
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/shipment")
public class SpringdataShipmentController {


    @Autowired
    private SpringDataShipmentDao springDataShipmentDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getAll() {

        return new ResponseEntity<>(springDataShipmentDao.findAll(), HttpStatus.OK);
    }

    @GetMapping("/resultForNow")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StockMaterialsRequests>> getResult() {

        return new ResponseEntity<>(springDataShipmentDao.resultForNow(), HttpStatus.OK);
    }

    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getDateShipmentHibernate(@PathVariable("beginning date") Date date1,
                                                                            @PathVariable("finish date") Date date2) {
        return new ResponseEntity<>(springDataShipmentDao.findByShipmentDateBetweenOrderByShipmentDate(date1, date2), HttpStatus.OK);
    }

    @GetMapping("/product_date")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getShipmentsByProductAndPeriod(String product, Date date1, Date date2) {
        return new ResponseEntity<>(springDataShipmentDao.findByShipmentProductAndShipmentDateBetween(product, date1, date2), HttpStatus.OK);
    }

    @GetMapping("/company_date")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getShipmentsByCompanyAndPeriod(String company, Date date1, Date date2) {
        return new ResponseEntity<>(springDataShipmentDao.findByShipmentToCompanyAndShipmentDateBetween(company, date1, date2), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<HibernateShipment>> getShipmentById(@ApiParam("HibernateShipment Path Id") @PathVariable Long id) {
        Optional<HibernateShipment> shipment = springDataShipmentDao.findById(id);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }


    @Autowired
    private SpringDataUserDao springDataUserDao;

    @Autowired
    private SpringDataProductCatalogDao springDataProductCatalogDao;

    @Autowired
    private SpringDataOperationCodesDao springDataOperationCodesDao;


    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateShipment> createShipment(@RequestBody ShipmentCreateRequest request) {
        var shipment = new HibernateShipment();

        shipment.setHibernateOperationCodes(springDataOperationCodesDao.findByPurpose(request.getOperationCodePurpose()));
        shipment.setShipmentProduct(springDataProductCatalogDao.findByProductName(request.getProductCatalogName()));
        shipment.setShipmentQuantity(request.getShipmentQuantity());
        shipment.setShipmentPrice(request.getShipmentPrice());
        shipment.setShipmentUser(springDataUserDao.findBySurnameOrderBySurname(request.getShipmentUserSurname()));

        HibernateShipment savedShipment = springDataShipmentDao.save(shipment);

        return new ResponseEntity<>(savedShipment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteShipment(@PathVariable("id") Long shipmentId) {
        springDataShipmentDao.deleteById(shipmentId);
        return new ResponseEntity<>(shipmentId, HttpStatus.OK);
    }

}
