package com.fyeeme.quasar.user.controller;

import com.fyeeme.quasar.base.entity.BaseFilter;
import com.fyeeme.quasar.security.exception.AssertEntity;
import com.fyeeme.quasar.security.exception.ErrorCode;
import com.fyeeme.quasar.user.entity.Role;
import com.fyeeme.quasar.user.entity.User;
import com.fyeeme.quasar.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import java.util.List;

@PermitAll
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public User create(@RequestBody User user) {
        AssertEntity.notNull(null, ErrorCode.USER_DISABLED, "User has been disabled");
        var savedUser = userService.create(user);
        return savedUser;
    }

    @PutMapping()
    @RolesAllowed({ Role.USER })
    public User update(@RequestBody User user) {
        var savedUser = userService.update(user);
        return savedUser;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        AssertEntity.notNull(null, ErrorCode.USER_NOT_FOUND);
        return null;
    }

    @PostMapping("/search")
    public List<User> findAll(@RequestBody BaseFilter filter) {
        var users = userService.listAll(filter);
        return users;
    }
}
