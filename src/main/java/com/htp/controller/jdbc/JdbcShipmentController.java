package com.htp.controller.jdbc;

import com.htp.controller.requests.ShipmentCreateRequest;
import com.htp.controller.requests.StockMaterialsRequests;
import com.htp.domain.jdbc.Shipment;
import com.htp.repository.jdbc.OperationCodesDao;
import com.htp.repository.jdbc.ProductCatalogDao;
import com.htp.repository.jdbc.UserDao;
import com.htp.repository.jdbc.impl.ShipmentDaoImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/jdbc/shipment")
public class JdbcShipmentController {


    @Autowired
    @Qualifier("shipmentDaoImpl")
    private ShipmentDaoImpl shipmentDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Shipment>> getAll() {

        return new ResponseEntity<>(shipmentDao.findAll(), HttpStatus.OK);
    }

    @GetMapping("/resultForNow")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StockMaterialsRequests>> getResult() {

        return new ResponseEntity<>(shipmentDao.findResult(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get shipment from server by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful getting shipment"),
            @ApiResponse(code = 400, message = "Invalid shipment ID supplied"),
            @ApiResponse(code = 401, message = "Lol kek"),
            @ApiResponse(code = 404, message = "role was not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Shipment> getShipmentById(@ApiParam("HibernateShipment Path Id") @PathVariable Long id) {
        Shipment shipment = shipmentDao.findById(id);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }


    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductCatalogDao productCatalogDao;

    @Autowired
    private OperationCodesDao operationCodesDao;


    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentCreateRequest request) {
        var shipment = new Shipment();

        shipment.setOperationCodeId(operationCodesDao.findByPurpose(request.getOperationCodePurpose()));
        shipment.setProductCatalogId(productCatalogDao.findByProductName(request.getProductCatalogName()));
        shipment.setShipmentQuantity(request.getShipmentQuantity());
        shipment.setShipmentPrice(request.getShipmentPrice());
        shipment.setShipmentUserId(userDao.findBySurname(request.getShipmentUserSurname()));

        Shipment savedShipment = shipmentDao.save(shipment);

        return new ResponseEntity<>(savedShipment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteShipment(@PathVariable("id") Long shipmentId) {
        shipmentDao.delete(shipmentId);
        return new ResponseEntity<>(shipmentId, HttpStatus.OK);
    }

}
