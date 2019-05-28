package com.htp.controller.hibernate;

import com.htp.controller.requests.CompaniesCreateRequest;
import com.htp.domain.hibernate.HibernateCompanies;
import com.htp.domain.jdbc.Companies;
import com.htp.repository.hibernate.HibernateCompaniesDao;
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
@RequestMapping(value = "/rest/hibernate/companies")
public class HibernateCompaniesController {

    @Autowired
    private HibernateCompaniesDao hibernateCompaniesDaoImpl;

    @GetMapping("/all_hibernate_companies")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateCompanies>> getCompaniesHibernate() {
        return new ResponseEntity<>(hibernateCompaniesDaoImpl.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get company from server by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful getting company"),
            @ApiResponse(code = 400, message = "Invalid company ID supplied"),
            @ApiResponse(code = 401, message = "Lol kek"),
            @ApiResponse(code = 404, message = "role was not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<HibernateCompanies> getCompanyById(@ApiParam("Company Path Id") @PathVariable Long id) {
        HibernateCompanies companies = hibernateCompaniesDaoImpl.findById(id);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateCompanies> createCompany(@RequestBody CompaniesCreateRequest request) {
        var companies = new HibernateCompanies();
        companies.setCompanyName(request.getCompanyName());
        companies.setCity(request.getCity());
        companies.setAddress(request.getAddress());

        HibernateCompanies savedCompany = hibernateCompaniesDaoImpl.save(companies);

        return new ResponseEntity<>(savedCompany, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateCompanies> updateCompany(@PathVariable("id") Long companyId,
                                                   @RequestBody CompaniesCreateRequest request) {
        HibernateCompanies companies = hibernateCompaniesDaoImpl.findById(companyId);

        companies.setCompanyName(request.getCompanyName());
        companies.setCity(request.getCity());
        companies.setAddress(request.getAddress());

        HibernateCompanies updatedCompany = hibernateCompaniesDaoImpl.update(companies);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteCompany(@PathVariable("id") Long companyId) {
        hibernateCompaniesDaoImpl.delete(companyId);
        return new ResponseEntity<>(companyId, HttpStatus.OK);
    }
}
