package com.htp.repository.jdbc;

import com.htp.domain.jdbc.User;
import com.htp.repository.GenericDao;

import java.util.List;

public interface UserDao extends GenericDao<User, Long> {
    //    HibernateUser findByLogin(String login);
//
//
//    List<Long> batchUpdate(List<HibernateUser> users);
//
    List<User> search(String query, Integer limit, Integer offset);

    Long findBySurname(String surname);

//    List<HibernateUser> search(String query);
//
//    HibernateUser searchByNameAndSurname(String query);
//
//    HibernateUser searchByBirthDate(Date date);
}
