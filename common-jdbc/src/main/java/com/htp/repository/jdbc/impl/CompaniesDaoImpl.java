package com.htp.repository.jdbc.impl;


import com.htp.domain.jdbc.Companies;
import com.htp.repository.jdbc.CompaniesDao;
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
public class CompaniesDaoImpl implements CompaniesDao {

    private static final String COMPANY_ID = "company_id";
    private static final String COMPANY_NAME = "company_name";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Companies getCompaniesRowMapper(ResultSet resultSet, int i) throws SQLException {

        var companies = new Companies();

        companies.setCompanyId(resultSet.getLong(COMPANY_ID));
        companies.setCompanyName(resultSet.getString(COMPANY_NAME));
        companies.setCity(resultSet.getString(CITY));
        companies.setAddress(resultSet.getString(ADDRESS));

        return companies;
    }

    @Override
    public List<Companies> findAll() {
        final String findAllQuery = "select * from companies";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getCompaniesRowMapper);
    }

    @Override
    public Companies findById(Long id) {
        final String findById = "select * from companies where company_id = :companyId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getCompaniesRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from companies where company_id = :companyId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Companies save(Companies entity) {
        final String createQuery = "INSERT INTO companies (company_name, city, address) VALUES (:name, :city, :address);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getCompanyName());
        params.addValue("city", entity.getCity());
        params.addValue("address", entity.getAddress());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdCompanyId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdCompanyId);
    }

    @Override
    public Companies update(Companies entity) {
        final String createQuery = "UPDATE companies set company_name = :name, city = :city, address = :address" +
                " where company_id = :companyId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getCompanyName());
        params.addValue("city", entity.getCity());
        params.addValue("address", entity.getAddress());

        params.addValue("companyId", entity.getCompanyId());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getCompanyId());
    }

    @Override
    public Long findByCompanyName(String companyName) {
        final String findByName = "select * from companies where lower(company_name) = :companyName";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyName", companyName.toLowerCase());

        return namedParameterJdbcTemplate.queryForObject(findByName, params, this::getCompaniesRowMapper).getCompanyId();
    }
}
