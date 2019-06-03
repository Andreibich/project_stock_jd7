package com.htp.requests.springdata;

import com.htp.requests.requests.UserCreateRequest;
import com.htp.domain.hibernate.HibernateUser;
import com.htp.repository.spingdata.SpringDataRoleDao;
import com.htp.repository.spingdata.SpringDataUserDao;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping(value = "/rest/springdata/users")
public class SpringdataUserController {

    @Autowired
    private SpringDataUserDao springDataUserDao;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateUser>> getUsers() {

        return new ResponseEntity<>(springDataUserDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<HibernateUser>> getUserById(@ApiParam("HibernateUser Path Id") @PathVariable Long id) {
        Optional<HibernateUser> user = springDataUserDao.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Autowired
    private SpringDataRoleDao springDataRoleDao;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibernateUser> createUser(@RequestBody UserCreateRequest request) {
        HibernateUser user = new HibernateUser();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setHibernateRole(springDataRoleDao.findByRoleName(request.getRoleName()));

        HibernateUser savedUser = springDataUserDao.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibernateUser> updateUser(@PathVariable("id") Long userId,
                                                    @RequestBody UserCreateRequest request) {

        if (springDataUserDao.findById(userId).isPresent()) {
            HibernateUser user = springDataUserDao.findById(userId).get();
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setLogin(request.getLogin());
            user.setPassword(request.getPassword());
            user.setHibernateRole(springDataRoleDao.findByRoleName(request.getRoleName()));

            HibernateUser updateUser = springDataUserDao.save(user);

            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Search user by query")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user update"), //OK
            @ApiResponse(code = 400, message = "Invalid query supplied"), //Invalid request
            @ApiResponse(code = 404, message = "HibernateUser was not found"), //Resourse not found
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibernateUser>> searchUsers(String query) {
        List<HibernateUser> searchResult = springDataUserDao.findByNameContainingOrSurnameContaining(query, query);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long userId) {
        springDataUserDao.deleteById(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
