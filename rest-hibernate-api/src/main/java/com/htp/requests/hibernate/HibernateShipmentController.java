package com.htp.requests.hibernate;

import com.htp.requests.requests.ShipmentCreateRequest;
import com.htp.domain.hibernate.HibernateShipment;
import com.htp.repository.hibernate.*;
import io.swagger.annotations.ApiParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/shipment")
public class HibernateShipmentController {

    @Autowired
    private HibernateShipmentDao hibernateShipmentDaoImpl;

    @GetMapping("/all_date_shipments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getDateShipmentHibernate(Date date1, Date date2) {
        return new ResponseEntity<>(hibernateShipmentDaoImpl.findByPeriod(date1, date2), HttpStatus.OK);
    }

    @GetMapping("/product_date_shipments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getProductDateShipmentHibernate(String product, Date date1, Date date2) {
        return new ResponseEntity<>(hibernateShipmentDaoImpl.findByProductAndPeriod(hibernateProductCatalogDaoImpl.findByProductName(product).getProductId(), date1, date2), HttpStatus.OK);
    }

    @GetMapping("/company_date_shipments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getCompanyDateShipmentHibernate(String company, Date date1, Date date2) {
        return new ResponseEntity<>(hibernateShipmentDaoImpl.findByCompanyAndPeriod(hibernateCompaniesDaoImpl.findByCompanyName(company).getCompanyId(), date1, date2), HttpStatus.OK);
    }


    @GetMapping("/all_hibernate_shipments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateShipment>> getShipmentHibernate() {
        return new ResponseEntity<>(hibernateShipmentDaoImpl.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateShipment> getHibernateShipmentById(@ApiParam("HibernateShipment Path Id") @PathVariable Long id) {
        HibernateShipment hibernateShipment = hibernateShipmentDaoImpl.findById(id);
        return new ResponseEntity<>(hibernateShipment, HttpStatus.OK);
    }

    @Autowired
    private HibernateUserDao hibernateUserDaoImpl;

    @Autowired
    private HibernateProductCatalogDao hibernateProductCatalogDaoImpl;

    @Autowired
    private HibernateOperationCodesDao hibernateOperationCodesDaoImpl;

    @Autowired
    private HibernateCompaniesDao hibernateCompaniesDaoImpl;


    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateShipment> createShipment(@RequestBody ShipmentCreateRequest request) {
        var shipment = new HibernateShipment();

        shipment.setHibernateOperationCodes(hibernateOperationCodesDaoImpl.findByPurpose(request.getOperationCodePurpose()));
        shipment.setShipmentProduct(hibernateProductCatalogDaoImpl.findByProductName(request.getProductCatalogName()));
        shipment.setShipmentQuantity(request.getShipmentQuantity());
        shipment.setShipmentPrice(request.getShipmentPrice());
        shipment.setShipmentUser(hibernateUserDaoImpl.findBySurname(request.getShipmentUserSurname()));

        HibernateShipment savedShipment = hibernateShipmentDaoImpl.save(shipment);

        return new ResponseEntity<>(savedShipment, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteHibernateShipment(@PathVariable("id") Long shipmentId) {
        hibernateShipmentDaoImpl.delete(shipmentId);
        return new ResponseEntity<>(shipmentId, HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateShipment> updateHibernateShipment(@RequestBody HibernateShipment hibernateShipment) {
        HibernateShipment savedShipment = hibernateShipmentDaoImpl.update(hibernateShipment);
        return new ResponseEntity<>(savedShipment, HttpStatus.OK);
    }
}
