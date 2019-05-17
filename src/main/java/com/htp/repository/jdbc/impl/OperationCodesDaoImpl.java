package com.htp.repository.jdbc.impl;


import com.htp.domain.jdbc.OperationCodes;
import com.htp.repository.jdbc.OperationCodesDao;
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
public class OperationCodesDaoImpl implements OperationCodesDao {

    private static final String OPERATION_CODE_ID = "operation_code_id";
    private static final String PURPOSE = "purpose";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private OperationCodes getOperationCodesRowMapper(ResultSet resultSet, int i) throws SQLException {

        var operationCodes = new OperationCodes();

        operationCodes.setOperationCodeId(resultSet.getLong(OPERATION_CODE_ID));
        operationCodes.setPurpose(resultSet.getString(PURPOSE));

        return operationCodes;
    }


    @Override
    public Long findByPurpose(String purpose) {
        final String findByPurpose = "select * from operation_codes where lower(purpose) = :purpose";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("purpose", purpose.toLowerCase());

        return namedParameterJdbcTemplate.queryForObject(findByPurpose, params, this::getOperationCodesRowMapper).getOperationCodeId();
    }

    @Override
    public List<OperationCodes> findAll() {
        final String findAllQuery = "select * from operation_codes";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getOperationCodesRowMapper);
    }

    @Override
    public OperationCodes findById(Long id) {
        final String findById = "select * from operation_codes where operation_code_id = :codeId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("codeId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getOperationCodesRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from operation_codes where operation_code_id = :codeId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("codeId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public OperationCodes save(OperationCodes entity) {
        final String createQuery = "INSERT INTO operation_codes (purpose) VALUES (:purpose);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("purpose", entity.getPurpose());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdCodeId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdCodeId);
    }

    @Override
    public OperationCodes update(OperationCodes entity) {
        final String createQuery = "UPDATE operation_codes set purpose = :purpose" +
                " where operation_code_id = :codeId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("purpose", entity.getPurpose());

        params.addValue("codeId", entity.getOperationCodeId());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getOperationCodeId());
    }
}
