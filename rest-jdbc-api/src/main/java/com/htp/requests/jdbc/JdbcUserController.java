package com.htp.requests.jdbc;


import com.htp.requests.requests.SearchCriteria;
import com.htp.requests.requests.UserCreateRequest;
import com.htp.domain.jdbc.User;
import com.htp.repository.jdbc.RoleDao;
import com.htp.repository.jdbc.UserDao;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController()
@CrossOrigin
@RequestMapping(value = "/rest/jdbc/users")
public class JdbcUserController {

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userDao.findAll(), HttpStatus.OK);
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
    public ResponseEntity<User> getUserById(@ApiParam("HibernateUser Path Id") @PathVariable Long id) {
        User user = userDao.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Autowired
    private RoleDao roleDao;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setRoleId(roleDao.findByRoleName(request.getRoleName().toLowerCase()));

        User savedUser = userDao.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Update user by userID")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
                                           @RequestBody UserCreateRequest request) {
        User user = userDao.findById(userId);
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setRoleId(roleDao.findByRoleName(request.getRoleName().toLowerCase()));

        User updateUser = userDao.update(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Search user by query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "limit of users", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "start node of users", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "query", value = "search query", required = true, dataType = "string", paramType = "query"),
    })
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> searchUsers(@ApiIgnore @ModelAttribute SearchCriteria search) {
        List<User> searchResult =
                userDao.search(
                        search.getQuery(),
                        search.getLimit(),
                        search.getOffset()
                );
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long userId) {
        userDao.delete(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
