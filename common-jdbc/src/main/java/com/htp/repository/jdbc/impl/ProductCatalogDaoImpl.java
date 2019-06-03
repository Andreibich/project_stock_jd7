package com.htp.repository.jdbc.impl;

import com.htp.domain.jdbc.ProductCatalog;
import com.htp.repository.jdbc.ProductCatalogDao;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class ProductCatalogDaoImpl implements ProductCatalogDao {

    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_UNIT = "product_unit";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ProductCatalog getProductCatalogRowMapper(ResultSet resultSet, int i) throws SQLException {

        var productCatalog = new ProductCatalog();

        productCatalog.setProductId(resultSet.getLong(PRODUCT_ID));
        productCatalog.setProductName(resultSet.getString(PRODUCT_NAME));
        productCatalog.setProductUnit(resultSet.getString(PRODUCT_UNIT));

        return productCatalog;
    }

    @Override
    public List<ProductCatalog> findAll() {
        final String findAllQuery = "select * from product_catalog";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getProductCatalogRowMapper);
    }

    @Override
    public ProductCatalog findById(Long id) {
        final String findById = "select * from product_catalog where product_id = :productId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getProductCatalogRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from product_catalog where product_id = :productId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public ProductCatalog save(ProductCatalog entity) {
        final String createQuery = "INSERT INTO product_catalog (product_name, product_unit) VALUES (:name, :unit);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getProductName());
        params.addValue("unit", entity.getProductUnit());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdProductId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdProductId);
    }

    @Override
    public ProductCatalog update(ProductCatalog entity) {
        final String createQuery = "UPDATE product_catalog set product_name = :name, product_unit = :unit" +
                " where product_id = :productId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getProductName());
        params.addValue("unit", entity.getProductUnit());

        params.addValue("productId", entity.getProductId());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getProductId());
    }

    @Override
    public Long findByProductName(String productName) {
        final String findByName = "select * from product_catalog where lower(product_name) = :name";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", productName.toLowerCase());

        return namedParameterJdbcTemplate.queryForObject(findByName, params, this::getProductCatalogRowMapper).getProductId();
    }
}
