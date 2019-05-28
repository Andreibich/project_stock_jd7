package com.htp.repository.jdbc.impl;

import com.htp.controller.requests.StockMaterialsRequests;
import com.htp.domain.jdbc.Shipment;
import com.htp.repository.jdbc.ShipmentDao;
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
public class ShipmentDaoImpl implements ShipmentDao {

    private static final String SHIPMENT_ID = "shipment_id";
    private static final String SHIPMENT_DATE = "shipment_date";
    private static final String RECEPIENT_COMPANY_ID = "recipient_company_id";
    private static final String PRODUCT_CATALOG_ID = "product_catalog_id";
    private static final String SHIPMENT_QUANTITY = "shipment_quantity";
    private static final String SHIPMENT_PRICE = "shipment_price";
    private static final String INVOICE_NUMBER = "invoice_number";
    private static final String PROXY_NUMBER = "proxy_number";
    private static final String SHIPMENT_USER_ID = "shipment_user_id";
    private static final String RECEPIENT_EMPLOYEE_NAME = "recepient_employee_name";
    private static final String OPERATION_CODE_ID = "operation_code_id";

    private static final String OPERATION_CODE_PURPOSE = "purpose";
    private static final String RECEPIENT_COMPANY_NAME = "company_name";
    private static final String PRODUCT_CATALOG_NAME = "product_name";
    private static final String PRODUCT_CATALOG_UNIT = "product_unit";
    private static final String SHIPMENT_USER_SURNAME = "surname";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Shipment getShipmentRowMapper(ResultSet resultSet, int i) throws SQLException {

        var shipment = new Shipment();

        shipment.setShipmentId(resultSet.getLong(SHIPMENT_ID));
        shipment.setOperationCodeId(resultSet.getLong(OPERATION_CODE_ID));
        shipment.setProductCatalogId(resultSet.getLong(PRODUCT_CATALOG_ID));
        shipment.setShipmentQuantity(resultSet.getDouble(SHIPMENT_QUANTITY));
        shipment.setShipmentPrice(resultSet.getDouble(SHIPMENT_PRICE));
        shipment.setShipmentDate(resultSet.getDate(SHIPMENT_DATE));
        shipment.setShipmentUserId(resultSet.getLong(SHIPMENT_USER_ID));
        shipment.setRecipientCompanyId(resultSet.getLong(RECEPIENT_COMPANY_ID));
        shipment.setInvoiceNumber(resultSet.getString(INVOICE_NUMBER));
        shipment.setProxyNumber(resultSet.getLong(PROXY_NUMBER));
        shipment.setRecipientEmployeeName(resultSet.getString(RECEPIENT_EMPLOYEE_NAME));

        return shipment;
    }

    private StockMaterialsRequests getMaterialsRowMapper(ResultSet resultSet, int i) throws SQLException {

        var stockMaterials = new StockMaterialsRequests();

        stockMaterials.setProductName(resultSet.getString(PRODUCT_CATALOG_NAME));
        stockMaterials.setProductUnit(resultSet.getString(PRODUCT_CATALOG_UNIT));
        stockMaterials.setQuantity(resultSet.getDouble(SHIPMENT_QUANTITY));
        stockMaterials.setPrice(resultSet.getDouble(SHIPMENT_PRICE));

        return stockMaterials;
    }

    @Override
    public List<Shipment> findAll() {
        final String findAllQuery = "select * from shipment";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getShipmentRowMapper);
    }

    @Override
    public Shipment findById(Long id) {
        final String findById = "select * from shipment where shipment_id = :shipmentId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shipmentId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getShipmentRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from shipment where shipment_id = :shipmentId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shipmentId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    public Shipment save(Shipment entity) {
        final String createQuery = "INSERT INTO shipment (operation_code_id, shipment_date, product_catalog_id, " +
                "shipment_quantity, shipment_price, recipient_company_id, shipment_user_id, invoice_number, " +
                "proxy_number, recepient_employee_name) VALUES ( :operationCodeId, :shipmentDate, :productCatalogId, " +
                ":shipmentQuantity, :shipmentPrice, :recipientCompanyId, :shipmentUserId, :invoiceNumber, :proxyNumber, " +
                ":recepientEmployeeName)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("operationCodeId", entity.getOperationCodeId());
        params.addValue("shipmentDate", new Date(new Timestamp(System.currentTimeMillis()).getTime()));
        params.addValue("productCatalogId", entity.getProductCatalogId());
        params.addValue("shipmentQuantity", entity.getShipmentQuantity());
        params.addValue("shipmentPrice", entity.getShipmentPrice());
        params.addValue("recipientCompanyId", entity.getRecipientCompanyId());
        params.addValue("shipmentUserId", entity.getShipmentUserId());
        params.addValue("invoiceNumber", entity.getInvoiceNumber());
        params.addValue("proxyNumber", entity.getProxyNumber());
        params.addValue("recipientEmployeeName", entity.getRecipientEmployeeName());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdShipmentId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdShipmentId);
    }

    @Override
    public Shipment update(Shipment entity) {
        return null;
    }

    @Override
    public List<StockMaterialsRequests> findResult() {
        final String resultQuery = "select t1.a1 as product_name, t1.a2 as product_unit, sum(t1.a3) as shipment_quantity, t1.a4 as shipment_price " +
                "FROM " +
                "(SELECT product_catalog.product_name as a1, product_catalog.product_unit as a2,  sum(receipt.receipt_quantity) as a3, receipt.receipt_price as a4 " +
                "FROM receipt " +
                "Inner JOIN product_catalog ON  product_catalog.product_id = receipt.product_catalog_id " +
                "GROUP BY product_catalog.product_id, receipt.receipt_price  " +
                "UNION " +
                "SELECT product_catalog.product_name, product_catalog.product_unit, -sum(shipment.shipment_quantity), shipment.shipment_price  " +
                "FROM shipment " +
                "Inner JOIN product_catalog ON  product_catalog.product_id = shipment.product_catalog_id " +
                "GROUP BY product_catalog.product_id) as t1 " +
                "GROUP BY t1.a1, t1.a2, t1.a4";
        return namedParameterJdbcTemplate.query(resultQuery, this::getMaterialsRowMapper);
    }
}
