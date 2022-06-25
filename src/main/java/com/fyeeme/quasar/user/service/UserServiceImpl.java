package com.fyeeme.quasar.user.service;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.base.repository.suppport.GenericSpecificationBuilder;
import com.fyeeme.quasar.core.exception.AssertEntity;
import com.fyeeme.quasar.core.exception.CommonError;
import com.fyeeme.quasar.user.entity.User;
import com.fyeeme.quasar.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
    public User getByUsername(String username) {
        var optional = repository.findByUsername(username);
        AssertEntity.isTrue(optional.isPresent(), CommonError.USER, CommonError.NOT_FOUND);
        // TODO solved open in view
        // https://www.baeldung.com/hibernate-initialize-proxy-exception
        var user = optional.get();
        log.info("user roles: {}", user.getRoles());
        return user;
    }

    @Override
    public Page<User> findAll(QueryCondition filter) {
        var specs = GenericSpecificationBuilder.buildSpecs(filter, User.class);
        return repository.findAll(specs, toPageRequest(filter));
    }

}
