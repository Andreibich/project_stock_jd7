package com.htp.repository.jdbc.impl;

import com.htp.domain.jdbc.Role;
import com.htp.repository.jdbc.RoleDao;
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
public class RoleDaoImpl implements RoleDao {

    private static final String ROLE_ID = "role_id";
    private static final String ROLE_NAME = "role_name";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Role getRoleRowMapper(ResultSet resultSet, int i) throws SQLException {

        var role = new Role();

        role.setRoleId(resultSet.getLong(ROLE_ID));
        role.setRoleName(resultSet.getString(ROLE_NAME));

        return role;
    }


    @Override
    public List<Role> findAll() {
        final String findAllQuery = "select * from role";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getRoleRowMapper);
    }

    @Override
    public Role findById(Long id) {
        final String findById = "select * from role where role_id = :roleId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roleId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getRoleRowMapper);
    }

    @Override
    public Long findByRoleName(String roleName) {
        final String findByName = "select * from role where lower(role_name) = :roleName";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roleName", roleName.toLowerCase());

        return namedParameterJdbcTemplate.queryForObject(findByName, params, this::getRoleRowMapper).getRoleId();
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from role where role_id = :roleId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roleId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Role save(Role entity) {
        final String createQuery = "INSERT INTO role (role_name) VALUES (:name);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getRoleName());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdRoleId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdRoleId);
    }

    @Override
    public Role update(Role entity) {
        final String createQuery = "UPDATE role set role_name = :name where role_id = :roleId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getRoleName());

        params.addValue("roleId", entity.getRoleId());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getRoleId());
    }
}
