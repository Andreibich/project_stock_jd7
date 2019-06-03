package com.htp.requests.jdbc;

import com.htp.requests.requests.RoleCreateRequest;
import com.htp.domain.jdbc.Role;
import com.htp.repository.jdbc.RoleDao;
import io.swagger.annotations.ApiParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/jdbc/roles")
public class JdbcRoleController {

    @Autowired
    @Qualifier("roleDaoImpl")
    private RoleDao roleDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Role>> getRoles() {

        return new ResponseEntity<>(roleDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Role> getRoleById(@ApiParam("HibernateRoleDao Path Id") @PathVariable Long id) {
        Role role = roleDao.findById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Role> createRole(@RequestBody RoleCreateRequest request) {
        var role = new Role();
        role.setRoleName(request.getRoleName());

        Role savedRole = roleDao.save(role);

        return new ResponseEntity<>(savedRole, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Role> updateRole(@PathVariable("id") Long roleId,
                                           @RequestBody RoleCreateRequest request) {
        Role role = roleDao.findById(roleId);
        role.setRoleName(request.getRoleName());

        Role updatedRole = roleDao.update(role);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteRole(@PathVariable("id") Long roleId) {
        roleDao.delete(roleId);
        return new ResponseEntity<>(roleId, HttpStatus.OK);
    }
}
