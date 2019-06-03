package com.htp.requests.jdbc;

import com.htp.requests.requests.ProductCatalogCreateRequest;
import com.htp.domain.jdbc.ProductCatalog;
import com.htp.repository.jdbc.ProductCatalogDao;
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
@RequestMapping(value = "/rest/jdbc/products")
public class JdbcProductCatalogController {

    @Autowired
    @Qualifier("productCatalogDaoImpl")
    private ProductCatalogDao productCatalogDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductCatalog>> getProducts() {

        return new ResponseEntity<>(productCatalogDao.findAll(), HttpStatus.OK);
    }


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
    public ResponseEntity<ProductCatalog> updateProduct(@PathVariable("id") Long productId,
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
