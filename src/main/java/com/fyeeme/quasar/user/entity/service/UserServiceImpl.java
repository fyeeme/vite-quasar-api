package com.fyeeme.quasar.user.entity.service;

import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import com.fyeeme.quasar.user.entity.User;
import com.fyeeme.quasar.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        user.setId(null);
        user.setEnabled(true);
        user.setLocked(false);
        var savedUser = repository.save(user);
        return savedUser;
    }


    @Override
    public User update(User user) {
        var existedUser = repository.getById(user.getId());
        existedUser.setNickname(user.getNickname());
        var savedUser = repository.save(existedUser);
        return savedUser;
    }


    @Override
    public List<User> listAll(QueryCondition filter) {
        return repository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        var optional = repository.findByUsername(username);
        return optional.orElse(null);
    }


}
