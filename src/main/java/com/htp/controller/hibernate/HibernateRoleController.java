package com.htp.controller.hibernate;

import com.htp.controller.requests.RoleCreateRequest;
import com.htp.domain.hibernate.HibernateRole;
import com.htp.domain.jdbc.Role;
import com.htp.repository.hibernate.HibernateRoleDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/roles")
public class HibernateRoleController {

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
    public ResponseEntity<HibernateRole> getRoleById(@ApiParam("HibernateRoleDao Path Id") @PathVariable Long id) {
        HibernateRole role = hibernateRoleDaoImpl.findById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateRole> createRole(@RequestBody RoleCreateRequest request) {
        var role = new HibernateRole();
        role.setRoleName(request.getRoleName());

        HibernateRole savedRole = hibernateRoleDaoImpl.save(role);

        return new ResponseEntity<>(savedRole, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateRole> updateRole(@PathVariable("id") Long roleId,
                                           @RequestBody RoleCreateRequest request) {
        HibernateRole role = hibernateRoleDaoImpl.findById(roleId);
        role.setRoleName(request.getRoleName());

        HibernateRole updatedRole = hibernateRoleDaoImpl.update(role);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteRole(@PathVariable("id") Long roleId) {
        hibernateRoleDaoImpl.delete(roleId);
        return new ResponseEntity<>(roleId, HttpStatus.OK);
    }
}
