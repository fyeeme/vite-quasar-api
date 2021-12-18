package com.fyeeme.quasar.user.service;

import com.fyeeme.quasar.base.entity.BaseFilter;
import com.fyeeme.quasar.base.service.CrudService;
import com.fyeeme.quasar.user.entity.User;

public interface UserService extends CrudService<User, BaseFilter> {

    User getByUsername(String username);
}
