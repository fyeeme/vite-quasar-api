package com.fyeeme.quasar.user.entity.service;

import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import com.fyeeme.quasar.base.service.ResourceService;
import com.fyeeme.quasar.user.entity.User;

public interface UserService extends ResourceService<User, QueryCondition> {

    User getByUsername(String username);
}
