package com.htp.repository.jdbc;

import com.htp.domain.jdbc.Role;
import com.htp.repository.GenericDao;

public interface RoleDao extends GenericDao<Role, Long> {
//    List<HibernateRoleDao> getRolesByUserId(Long userId);

    Long findByRoleName(String roleName);
}
