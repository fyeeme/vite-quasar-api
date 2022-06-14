package com.fyeeme.quasar.user.controller;

import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import com.fyeeme.quasar.security.exception.AssertEntity;
import com.fyeeme.quasar.security.exception.CommonError;
import com.fyeeme.quasar.user.entity.Role;
import com.fyeeme.quasar.user.entity.User;
import com.fyeeme.quasar.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@PermitAll
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User create(@RequestBody User user) {
        AssertEntity.notNull(null, CommonError.USER, CommonError.DISABLED);

        var savedUser = userService.create(user);
        return savedUser;
    }

    @PutMapping()
    @RolesAllowed({Role.USER})
    public User update(@RequestBody User user) {
        var savedUser = userService.update(user);
        return savedUser;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        AssertEntity.notNull(null, CommonError.USER, CommonError.NOT_FOUND);
        return null;
    }

    @PostMapping("/search")
    public List<User> findAll(@RequestBody QueryCondition filter) {
        var users = userService.listAll(filter);
        return users;
    }
}
