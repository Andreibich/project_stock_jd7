package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataUserDao extends JpaRepository<HibernateUser, Long> {

    List<HibernateUser> findByNameContainingOrSurnameContaining(String queryName, String querySurname);

    Long findBySurname(String surname);

    HibernateUser findBySurnameOrderBySurname(String surname);
}
