package com.fyeeme.quasar.user.controller;

import com.fyeeme.quasar.base.entity.BaseFilter;
import com.fyeeme.quasar.user.entity.User;
import com.fyeeme.quasar.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.PermitAll;
import java.util.List;

@PermitAll
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public User create(@RequestBody User user) {
        var savedUser = userService.create(user);
        return savedUser;
    }

    @PutMapping()
    public User update(@RequestBody User user) {
        var savedUser = userService.update(user);
        return savedUser;
    }

    @PostMapping("/search")
    public List<User> findAll(@RequestBody BaseFilter filter) {
        var users = userService.listAll(filter);
        return users;
    }
}
