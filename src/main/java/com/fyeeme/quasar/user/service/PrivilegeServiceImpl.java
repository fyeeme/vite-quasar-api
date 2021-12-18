package com.fyeeme.quasar.user.service;

import com.fyeeme.quasar.base.entity.BaseFilter;
import com.fyeeme.quasar.user.entity.Privilege;
import com.fyeeme.quasar.user.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository repository;

    @Override
    public List<Privilege> listAll(BaseFilter filter) {
        return repository.findAll();
    }

}
