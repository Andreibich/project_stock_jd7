package com.htp.requests.springdata;

import com.htp.requests.requests.ShipmentCreateRequest;
import com.htp.requests.requests.StockMaterialsRequests;
import com.htp.domain.hibernate.HibernateShipment;
import com.htp.repository.spingdata.SpringDataOperationCodesDao;
import com.htp.repository.spingdata.SpringDataProductCatalogDao;
import com.htp.repository.spingdata.SpringDataShipmentDao;
import com.htp.repository.spingdata.SpringDataUserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
    public ResponseEntity<HibernateShipment> getShipmentById(@ApiParam("HibernateShipment Path Id") @PathVariable Long id) {
        HibernateShipment shipment = springDataShipmentDao.findByShipmentId(id);
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
        HibernateShipment shipment = new HibernateShipment();

        shipment.setHibernateOperationCodes(springDataOperationCodesDao.findByPurpose(request.getOperationCodePurpose()));
        shipment.setShipmentProduct(springDataProductCatalogDao.findByProductName(request.getProductCatalogName()));
        shipment.setShipmentQuantity(request.getShipmentQuantity());
        shipment.setShipmentPrice(request.getShipmentPrice());
        shipment.setShipmentUser(springDataUserDao.findBySurname(request.getShipmentUserSurname()));
        shipment.setShipmentDate(new Date(new Timestamp(System.currentTimeMillis()).getTime()));

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
