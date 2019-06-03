package com.htp.repository.spingdata;

import com.htp.domain.hibernate.HibernateRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SpringDataRoleDao extends JpaRepository<HibernateRole, Long> {

    HibernateRole findByRoleName(String roleName);
}
