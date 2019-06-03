package com.htp.requests.jdbc;


import com.htp.requests.requests.OperationCodesCreateRequest;
import com.htp.domain.jdbc.OperationCodes;
import com.htp.repository.jdbc.OperationCodesDao;
import io.swagger.annotations.ApiParam;
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
@RequestMapping(value = "/rest/jdbc/operation_codes")
public class JdbcOperationCodesController {

    @Autowired
    @Qualifier("operationCodesDaoImpl")
    private OperationCodesDao operationCodesDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OperationCodes>> getCodes() {

        return new ResponseEntity<>(operationCodesDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OperationCodes> getCodeById(@ApiParam("Code Path Id") @PathVariable Long id) {
        OperationCodes operationCodes = operationCodesDao.findById(id);
        return new ResponseEntity<>(operationCodes, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OperationCodes> createOperationCode(@RequestBody OperationCodesCreateRequest request) {
        var operationCodes = new OperationCodes();
        operationCodes.setPurpose(request.getPurpose());

        OperationCodes savedOperationCodes = operationCodesDao.save(operationCodes);

        return new ResponseEntity<>(savedOperationCodes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OperationCodes> updateCode(@PathVariable("id") Long codeId,
                                                     @RequestBody OperationCodesCreateRequest request) {
        OperationCodes operationCodes = operationCodesDao.findById(codeId);

        operationCodes.setPurpose(request.getPurpose());

        OperationCodes updatedCode = operationCodesDao.update(operationCodes);
        return new ResponseEntity<>(updatedCode, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteCode(@PathVariable("id") Long codeId) {
        operationCodesDao.delete(codeId);
        return new ResponseEntity<>(codeId, HttpStatus.OK);
    }
}
