package com.fyeeme.quasar.user.repository;

import com.fyeeme.quasar.base.repository.ResourceRepository;
import com.fyeeme.quasar.user.entity.User;

import java.util.Optional;

public interface UserRepository extends ResourceRepository<User> {

    Optional<User> findByUsername(String username);
}
