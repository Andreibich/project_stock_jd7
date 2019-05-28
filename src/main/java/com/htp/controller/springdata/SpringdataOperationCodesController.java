package com.htp.controller.springdata;


import com.htp.controller.requests.OperationCodesCreateRequest;
import com.htp.domain.hibernate.HibernateOperationCodes;
import com.htp.repository.spingdata.SpringDataOperationCodesDao;
import io.swagger.annotations.ApiParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/operation_codes")
public class SpringdataOperationCodesController {

    @Autowired
    private SpringDataOperationCodesDao springDataOperationCodesDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateOperationCodes>> getCodes() {

        return new ResponseEntity<>(springDataOperationCodesDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<HibernateOperationCodes>> getCodeById(@ApiParam("Code Path Id") @PathVariable Long id) {
        Optional<HibernateOperationCodes> operationCodes = springDataOperationCodesDao.findById(id);
        return new ResponseEntity<>(operationCodes, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateOperationCodes> createOperationCode(@RequestBody OperationCodesCreateRequest request) {
        var operationCodes = new HibernateOperationCodes();
        operationCodes.setPurpose(request.getPurpose());

        HibernateOperationCodes savedOperationCodes = springDataOperationCodesDao.save(operationCodes);

        return new ResponseEntity<>(savedOperationCodes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateOperationCodes> updateCode(@PathVariable("id") Long codeId,
                                                              @RequestBody OperationCodesCreateRequest request) {
        var optionalHibernateOperationCodes = springDataOperationCodesDao.findById(codeId);
        if (optionalHibernateOperationCodes.isPresent()) {
            HibernateOperationCodes operationCodes = optionalHibernateOperationCodes.get();
            operationCodes.setPurpose(request.getPurpose());

            HibernateOperationCodes updatedCode = springDataOperationCodesDao.save(operationCodes);
            return new ResponseEntity<>(updatedCode, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteCode(@PathVariable("id") Long codeId) {
        springDataOperationCodesDao.deleteById(codeId);
        return new ResponseEntity<>(codeId, HttpStatus.OK);
    }
}
