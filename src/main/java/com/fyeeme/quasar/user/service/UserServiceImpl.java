package com.fyeeme.quasar.user.service;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.base.repository.support.GenericSpecificationBuilder;
import com.fyeeme.quasar.core.exception.AssertEntity;
import com.fyeeme.quasar.core.exception.CommonError;
import com.fyeeme.quasar.user.entity.User;
import com.fyeeme.quasar.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        Optional<User> optional = repository.findById(user.getId());
        AssertEntity.notNull(optional.orElse(null), User.class, CommonError.NOT_FOUND);

        User existedUser = optional.get();
        existedUser.setNickname(user.getNickname());
        return repository.save(existedUser);
    }

    @Override
    public User getByUsername(String username) {
        var optional = repository.findByUsername(username);
        AssertEntity.isTrue(optional.isPresent(), User.class, CommonError.NOT_FOUND);
        // TODO solved open in view
        // https://www.baeldung.com/hibernate-initialize-proxy-exception
        User user = optional.get();
        log.info("user roles: {}", user.getRoles());
        return user;
    }

    @Override
    public Page<User> findAll(QueryCondition filter) {
        Specification<User> specs = GenericSpecificationBuilder.buildSpecs(filter, User.class);
        return repository.findAll(specs, toPageRequest(filter));
    }

}
