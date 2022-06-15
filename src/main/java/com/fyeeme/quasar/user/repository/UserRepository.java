package com.fyeeme.quasar.user.repository;

import com.fyeeme.quasar.base.repository.ResourceRepository;
import com.fyeeme.quasar.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends ResourceRepository<User> {

    @Query("SELECT u FROM User u JOIN FETCH u.roles  where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
