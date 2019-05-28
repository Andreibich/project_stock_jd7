package com.htp.controller.springdata;

import com.htp.controller.requests.ProductCatalogCreateRequest;
import com.htp.domain.hibernate.HibernateProductCatalog;
import com.htp.repository.spingdata.SpringDataProductCatalogDao;
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
@RequestMapping(value = "/rest/springdata/products")
public class SpringdataProductCatalogController {

    @Autowired
    private SpringDataProductCatalogDao springDataProductCatalogDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateProductCatalog>> getProducts() {

        return new ResponseEntity<>(springDataProductCatalogDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<HibernateProductCatalog>> getProductById(@ApiParam("Product Path Id") @PathVariable Long id) {
        Optional<HibernateProductCatalog> productCatalog = springDataProductCatalogDao.findById(id);
        return new ResponseEntity<>(productCatalog, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateProductCatalog> createProduct(@RequestBody ProductCatalogCreateRequest request) {
        var productCatalog = new HibernateProductCatalog();

        productCatalog.setProductName(request.getProductName());
        productCatalog.setProductUnit(request.getProductUnit());


        HibernateProductCatalog savedProduct = springDataProductCatalogDao.save(productCatalog);

        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateProductCatalog> updateProduct(@PathVariable("id") Long productId,
                                                                 @RequestBody ProductCatalogCreateRequest request) {
        var optionalHibernateProductCatalog = springDataProductCatalogDao.findById(productId);
        if (optionalHibernateProductCatalog.isPresent()) {
            HibernateProductCatalog productCatalog = optionalHibernateProductCatalog.get();

            productCatalog.setProductName(request.getProductName());
            productCatalog.setProductUnit(request.getProductUnit());

            HibernateProductCatalog updatedProduct = springDataProductCatalogDao.save(productCatalog);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") Long productId) {
        springDataProductCatalogDao.deleteById(productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }
}
