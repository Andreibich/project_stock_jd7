package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SpringDataUserDao extends JpaRepository<HibernateUser, Long> {

    List<HibernateUser> findByNameContainingOrSurnameContaining(String queryName, String querySurname);

    HibernateUser findBySurname(String surname);

}
