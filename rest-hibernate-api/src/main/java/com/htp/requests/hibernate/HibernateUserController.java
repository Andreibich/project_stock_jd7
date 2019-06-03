package com.htp.requests.hibernate;

import com.htp.requests.requests.UserCreateRequest;
import com.htp.domain.hibernate.HibernateUser;
import com.htp.repository.hibernate.HibernateRoleDao;
import com.htp.repository.hibernate.HibernateUserDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/users")
public class HibernateUserController {

    @Autowired
    private HibernateUserDao hibernateUserDaoImpl;

    @GetMapping("/all_hibernate_user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateUser>> getUsersHibernate() {
        return new ResponseEntity<>(hibernateUserDaoImpl.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get user from server by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful getting user"),
            @ApiResponse(code = 400, message = "Invalid HibernateUser ID supplied"),
            @ApiResponse(code = 401, message = "Lol kek"),
            @ApiResponse(code = 404, message = "HibernateUser was not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<HibernateUser> getUserById(@ApiParam("HibernateUser Path Id") @PathVariable Long id) {
        HibernateUser user = hibernateUserDaoImpl.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Autowired
    private HibernateRoleDao hibernateRoleDaoImpl;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateUser> createUser(@RequestBody UserCreateRequest request) {
        HibernateUser user = new HibernateUser();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setHibernateRole(hibernateRoleDaoImpl.findByRoleName(request.getRoleName().toLowerCase()));

        HibernateUser savedUser = hibernateUserDaoImpl.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Update user by userID")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateUser> updateUser(@PathVariable("id") Long userId,
                                                    @RequestBody UserCreateRequest request) {
        HibernateUser user = hibernateUserDaoImpl.findById(userId);
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setHibernateRole(hibernateRoleDaoImpl.findByRoleName(request.getRoleName().toLowerCase()));

        HibernateUser updateUser = hibernateUserDaoImpl.update(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long userId) {
        hibernateUserDaoImpl.delete(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
