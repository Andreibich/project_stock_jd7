package com.htp.controller.hibernate;

import com.htp.controller.requests.ShipmentCreateRequest;
import com.htp.domain.hibernate.HibernateShipment;
import com.htp.repository.hibernate.HibernateOperationCodesDao;
import com.htp.repository.hibernate.HibernateProductCatalogDao;
import com.htp.repository.hibernate.HibernateShipmentDao;
import com.htp.repository.hibernate.HibernateUserDao;
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
    public ResponseEntity<List<HibernateShipment>> getDateShipmentHibernate(@PathVariable("beginning date") Date date1,
                                                                            @PathVariable("finish date") Date date2) {
        return new ResponseEntity<>(hibernateShipmentDaoImpl.findByPeriod(date1, date2), HttpStatus.OK);
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

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateShipment> updateHibernateShipment(@RequestBody HibernateShipment hibernateShipment) {
        HibernateShipment savedShipment = hibernateShipmentDaoImpl.update(hibernateShipment);
        return new ResponseEntity<>(savedShipment, HttpStatus.OK);
    }
}
