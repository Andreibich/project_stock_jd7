package com.htp.repository.jdbc.impl;


import com.htp.controller.requests.ReceiptResultRequest;
import com.htp.domain.jdbc.Receipt;
import com.htp.repository.jdbc.ReceiptDao;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class ReceiptDaoImpl implements ReceiptDao {

    private static final String RECEIPT_ID = "receipt_id";
    private static final String SUPPLIER_ID = "supplier_id";
    private static final String RECEIPT_DATE = "receipt_date";
    private static final String RECEIPT_PRICE = "receipt_price";
    private static final String RECEIPT_QUANTITY = "receipt_quantity";
    private static final String PRODUCT_CATALOG_ID = "product_catalog_id";
    private static final String RECEIPT_USER_ID = "receipt_user_id";
    private static final String INVOICE_NUMBER = "invoice_number";

    private static final String COMPANY_NAME = "company_name";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_UNIT = "product_unit";
    private static final String USER_SURNAME = "surname";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Receipt getReceiptRowMapper(ResultSet resultSet, int i) throws SQLException {

        var receipt = new Receipt();

        receipt.setReceiptId(resultSet.getLong(RECEIPT_ID));
        receipt.setSupplierId(resultSet.getLong(SUPPLIER_ID));
        receipt.setProductCatalogId(resultSet.getLong(PRODUCT_CATALOG_ID));
        receipt.setReceiptQuantity(resultSet.getDouble(RECEIPT_QUANTITY));
        receipt.setReceiptPrice(resultSet.getDouble(RECEIPT_PRICE));
        receipt.setReceiptDate(resultSet.getDate(RECEIPT_DATE));
        receipt.setInvoiceNumber(resultSet.getString(INVOICE_NUMBER));
        receipt.setReceiptUserId(resultSet.getLong(RECEIPT_USER_ID));

        return receipt;
    }

    private ReceiptResultRequest getAllReceiptRowMapper(ResultSet set, int i) throws SQLException {

        var receipt = new ReceiptResultRequest();

        receipt.setReceiptId(set.getLong(RECEIPT_ID));
        receipt.setSupplierName(set.getString(COMPANY_NAME));
        receipt.setProductCatalogName(set.getString(PRODUCT_NAME));
        receipt.setProductCatalogUnit(set.getString(PRODUCT_UNIT));
        receipt.setReceiptQuantity(set.getDouble(RECEIPT_QUANTITY));
        receipt.setReceiptPrice(set.getDouble(RECEIPT_PRICE));
        receipt.setReceiptDate(set.getDate(RECEIPT_DATE));
        receipt.setInvoiceNumber(set.getString(INVOICE_NUMBER));
        receipt.setReceiptUserSurname(set.getString(USER_SURNAME));

        return receipt;
    }

    @Override
    public List<Receipt> findAll() {
        final String findAllQuery = "select * from receipt";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getReceiptRowMapper);
    }


    @Override
    public List<ReceiptResultRequest> findAllReceipts() {
        final String findAllQuery = "select receipt.receipt_id, companies.company_name, product_catalog.product_name, " +
                "product_catalog.product_unit, receipt.receipt_quantity, receipt.receipt_price, receipt.receipt_date, " +
                "receipt.invoice_number, user.surname from receipt inner join companies ON receipt.supplier_id = " +
                "companies.company_id inner join product_catalog on product_catalog.product_id = receipt.product_catalog_id " +
                "inner join user on user.user_id = receipt.receipt_user_id order by receipt.receipt_date desc";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getAllReceiptRowMapper);
    }

    @Override
    public Receipt findById(Long id) {
        final String findById = "select * from receipt where receipt_id = :receiptId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("receiptId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getReceiptRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from receipt where receipt_id = :receiptId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("receiptId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    public Receipt save(Receipt entity) {
        final String createQuery = "INSERT INTO receipt (receipt_date, product_catalog_id, receipt_quantity, receipt_price, " +
                "supplier_id, receipt_user_id, invoice_number) VALUES ( :dateReceipt, :productId, :quantity, :price, :supplierId," +
                ":userId, :invoiceNumber)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dateReceipt", new Date(new Timestamp(System.currentTimeMillis()).getTime()));
        params.addValue("productId", entity.getProductCatalogId());
        params.addValue("quantity", entity.getReceiptQuantity());
        params.addValue("price", entity.getReceiptPrice());
        params.addValue("supplierId", entity.getSupplierId());
        params.addValue("userId", entity.getReceiptUserId());
        params.addValue("invoiceNumber", entity.getInvoiceNumber());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdReceiptId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdReceiptId);
    }

    @Override
    public Receipt update(Receipt entity) {
        return null;
    }
}
