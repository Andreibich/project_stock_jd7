package com.htp.requests.hibernate;


import com.htp.requests.requests.OperationCodesCreateRequest;
import com.htp.domain.hibernate.HibernateOperationCodes;
import com.htp.repository.hibernate.HibernateOperationCodesDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/operation_codes")
public class HibernateOperationCodesController {

    @Autowired
    private HibernateOperationCodesDao hibernateOperationCodesDaoImpl;

    @GetMapping("/all_operation_codes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateOperationCodes>> getOperationCodesHibernate() {
        return new ResponseEntity<>(hibernateOperationCodesDaoImpl.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get operation code from server by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful getting code"),
            @ApiResponse(code = 400, message = "Invalid code ID supplied"),
            @ApiResponse(code = 401, message = "Lol kek"),
            @ApiResponse(code = 404, message = "role was not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<HibernateOperationCodes> getCodeById(@ApiParam("Code Path Id") @PathVariable Long id) {
        HibernateOperationCodes operationCodes = hibernateOperationCodesDaoImpl.findById(id);
        return new ResponseEntity<>(operationCodes, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateOperationCodes> createOperationCode(@RequestBody OperationCodesCreateRequest request) {
        var operationCodes = new HibernateOperationCodes();
        operationCodes.setPurpose(request.getPurpose());

        HibernateOperationCodes savedOperationCodes = hibernateOperationCodesDaoImpl.save(operationCodes);

        return new ResponseEntity<>(savedOperationCodes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateOperationCodes> updateCode(@PathVariable("id") Long codeId,
                                                   @RequestBody OperationCodesCreateRequest request) {
        HibernateOperationCodes operationCodes = hibernateOperationCodesDaoImpl.findById(codeId);

        operationCodes.setPurpose(request.getPurpose());

        HibernateOperationCodes updatedCode = hibernateOperationCodesDaoImpl.update(operationCodes);
        return new ResponseEntity<>(updatedCode, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteCode(@PathVariable("id") Long codeId) {
        hibernateOperationCodesDaoImpl.delete(codeId);
        return new ResponseEntity<>(codeId, HttpStatus.OK);
    }
}
