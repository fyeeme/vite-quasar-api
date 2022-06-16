package com.fyeeme.quasar.user.service;

import com.fyeeme.quasar.base.entity.QueryCondition;
import com.fyeeme.quasar.base.service.ResourceService;
import com.fyeeme.quasar.user.entity.User;

public interface UserService extends ResourceService<User, QueryCondition> {

    User getByUsername(String username);
}
