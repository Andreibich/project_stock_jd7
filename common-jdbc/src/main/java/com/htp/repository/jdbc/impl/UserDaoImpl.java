package com.htp.repository.jdbc.impl;

import com.htp.domain.jdbc.User;
import com.htp.repository.jdbc.UserDao;
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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    private static final String USER_ID = "user_id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String EMAIL = "email";
    private static final String CREATION_DATE = "creation_date";
    private static final String ROLE_ID = "role_id";
    private static final String LAST_ID = "lastId";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private User getUserRowMapper(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setUserId(resultSet.getLong(USER_ID));
        user.setName(resultSet.getString(NAME));
        user.setSurname(resultSet.getString(SURNAME));
        user.setLogin(resultSet.getString(LOGIN));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setPhoneNumber(resultSet.getString(PHONE_NUMBER));
        user.setEmail(resultSet.getString(EMAIL));
        user.setCreationDate(resultSet.getTimestamp(CREATION_DATE));
        user.setRoleId(resultSet.getLong(ROLE_ID));

        return user;
    }

    @Override
    public List<User> findAll() {
        final String findAllQuery = "select * from user";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getUserRowMapper);
    }

    @Override
    public User findById(Long id) {
//        final String findById = "select * from user where user_id = ?";
//        return jdbcTemplate.queryForObject(findById, new Object[]{id}, this::getUserRowMapper);
        final String findById = "select * from user where user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getUserRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from user where user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public User save(User entity) {
        final String createQuery = "INSERT INTO user (name, surname, login, password, phone_number, email, creation_date, role_id) " +
                "VALUES (:name, :surname, :login, :password, :phone_number, :email, :creation_date, :role_id);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("surname", entity.getSurname());
        params.addValue("login", entity.getLogin());
        params.addValue("password", entity.getPassword());
        params.addValue("phone_number", entity.getPhoneNumber());
        params.addValue("email", entity.getEmail());
        params.addValue("creation_date", new Date(new Timestamp(System.currentTimeMillis()).getTime()));
        params.addValue("role_id", entity.getRoleId());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(createdUserId);
    }

    @Override
    public User update(User entity) {
        final String createQuery = "UPDATE user set name = :name, surname = :surname, " +
                "login = :login, password = :password, phone_number = :phone_number, email = :email, " +
                "creation_date = :creation_date, role_id = :role_id where user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("surname", entity.getSurname());
        params.addValue("login", entity.getLogin());
        params.addValue("password", entity.getPassword());
        params.addValue("phone_number", entity.getPhoneNumber());
        params.addValue("email", entity.getEmail());
        params.addValue("creation_date", new Date(new Timestamp(System.currentTimeMillis()).getTime()));
        params.addValue("role_id", entity.getRoleId());

        params.addValue("userId", entity.getUserId());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getUserId());
    }

    //    @Override
//    public HibernateUser findByLogin(String login) {
//        final String findById = "select * from user where login = :login";
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("login", login);
//
//        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getUserRowMapper);
//    }
//
//    @Override
//    public HibernateUser searchByNameAndSurname(String query) {
//        final String findByNameAndSurname = "select * from user where lower(user_name) like lower(:query) or lower(user_surname) like lower(:query)";
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("query", "%" + query.toLowerCase() + "%");
//
//
//        return namedParameterJdbcTemplate.queryForObject(findByNameAndSurname, params, this::getUserRowMapper);
//    }
//
//    @Override
//    public HibernateUser searchByBirthDate(Date birthDate) {
//        final String findByBirthDate = "select * from user where birth_date = :birthDate";
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("birthDate", birthDate);
//
//        return namedParameterJdbcTemplate.queryForObject(findByBirthDate, params, this::getUserRowMapper);
//    }
//
//    @Override
//    public List<Long> batchUpdate(List<HibernateUser> users) {
//        final String createQuery = "UPDATE user set user_name = :userName, user_surname = :userSurname, " +
//                "birth_date = :birthDate, dep_id = :depId, login = :login, password = :pass where user_id = :userId";
//
//        List<SqlParameterSource> batch = new ArrayList<>();
//        for (HibernateUser user : users) {
//            MapSqlParameterSource params = new MapSqlParameterSource();
//            params.addValue("userName", user.getUserName());
//            params.addValue("userSurname", user.getUserSurname());
//            params.addValue("birthDate", user.getBirthDate());
//            params.addValue("depId", user.getDepartmentId());
//            params.addValue("login", user.getLogin());
//            params.addValue("pass", user.getPassword());
//
//            params.addValue("userId", user.getUserId());
//            batch.add(params);
//        }
//
//        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));
//        return users.stream().map(HibernateUser::getUserId).collect(Collectors.toList());
//
//    }
//
//    @Override
//    public List<HibernateUser> search(String query) {
//        return null;
//    }
//
    @Override
    public List<User> search(String query, Integer limit, Integer offset) {
        final String searchQuery = "select * from user where lower(name) LIKE lower(:query) or " +
                "lower(surname) LIKE lower(:query) limit :lim offset :off";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("lim", limit);
        params.addValue("off", offset);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getUserRowMapper);
    }

    @Override
    public Long findBySurname(String surname) {
        final String findBySurname = "select * from user where lower(surname) = :surname";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("surname", surname.toLowerCase());

        return namedParameterJdbcTemplate.queryForObject(findBySurname, params, this::getUserRowMapper).getUserId();
    }
}
