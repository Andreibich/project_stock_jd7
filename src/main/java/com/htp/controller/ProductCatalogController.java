package com.htp.controller;

import com.htp.controller.requests.ProductCatalogCreateRequest;
import com.htp.domain.hibernate.HibernateProductCatalog;
import com.htp.domain.hibernate.HibernateUser;
import com.htp.domain.jdbc.ProductCatalog;
import com.htp.repository.hibernate.HibernateProductCatalogDao;
import com.htp.repository.hibernate.HibernateUserDao;
import com.htp.repository.jdbc.ProductCatalogDao;
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
@RequestMapping(value = "/rest/products")
public class ProductCatalogController {

    @Autowired
    @Qualifier("productCatalogDaoImpl")
    private ProductCatalogDao productCatalogDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductCatalog>> getProducts() {

        return new ResponseEntity<>(productCatalogDao.findAll(), HttpStatus.OK);
    }

    @Autowired
    private HibernateProductCatalogDao hibernateProductCatalogDaoImpl;

    @GetMapping("/all_hibernate_products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateProductCatalog>> getProductsHibernate() {
        return new ResponseEntity<>(hibernateProductCatalogDaoImpl.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get product from server by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful getting product"),
            @ApiResponse(code = 400, message = "Invalid product ID supplied"),
            @ApiResponse(code = 401, message = "Lol kek"),
            @ApiResponse(code = 404, message = "role was not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductCatalog> getProductById(@ApiParam("Product Path Id") @PathVariable Long id) {
        ProductCatalog productCatalog = productCatalogDao.findById(id);
        return new ResponseEntity<>(productCatalog, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductCatalog> createProduct(@RequestBody ProductCatalogCreateRequest request) {
        var productCatalog = new ProductCatalog();

        productCatalog.setProductName(request.getProductName());
        productCatalog.setProductUnit(request.getProductUnit());


        ProductCatalog savedProduct = productCatalogDao.save(productCatalog);

        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductCatalog> updateroduct(@PathVariable("id") Long productId,
                                                   @RequestBody ProductCatalogCreateRequest request) {
        ProductCatalog productCatalog = productCatalogDao.findById(productId);

        productCatalog.setProductName(request.getProductName());
        productCatalog.setProductUnit(request.getProductUnit());

        ProductCatalog updatedProduct = productCatalogDao.update(productCatalog);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") Long productId) {
        productCatalogDao.delete(productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }
}
