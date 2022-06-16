package com.fyeeme.quasar.user.controller;

import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import com.fyeeme.quasar.core.exception.AssertEntity;
import com.fyeeme.quasar.core.exception.CommonError;
import com.fyeeme.quasar.user.entity.Role;
import com.fyeeme.quasar.user.entity.User;
import com.fyeeme.quasar.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

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
    public Page<User> findAll(@RequestBody QueryCondition filter) {
        var users = userService.findAll(filter);
        return users;
    }
}
