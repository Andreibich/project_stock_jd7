package com.htp.controller;

import com.htp.controller.requests.RoleCreateRequest;
import com.htp.domain.hibernate.HibernateRole;
import com.htp.domain.hibernate.HibernateUser;
import com.htp.domain.jdbc.Role;
import com.htp.repository.hibernate.HibernateRoleDao;
import com.htp.repository.jdbc.RoleDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping(value = "/rest/roles")
public class RoleController {

    @Autowired
    @Qualifier("roleDaoImpl")
    private RoleDao roleDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Role>> getRoles() {

        return new ResponseEntity<>(roleDao.findAll(), HttpStatus.OK);
    }


    @Autowired
    private HibernateRoleDao hibernateRoleDaoImpl;

    @GetMapping("/all_hibernate_user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateRole>> getRolesHibernate() {
        return new ResponseEntity<>(hibernateRoleDaoImpl.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get role from server by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful getting role"),
            @ApiResponse(code = 400, message = "Invalid role ID supplied"),
            @ApiResponse(code = 401, message = "Lol kek"),
            @ApiResponse(code = 404, message = "role was not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
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
