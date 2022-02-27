package com.fyeeme.quasar.user.service;

import com.fyeeme.quasar.base.repository.dto.QueryCondition;
import com.fyeeme.quasar.user.entity.Privilege;
import com.fyeeme.quasar.user.repository.PrivilegeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository repository;

    public PrivilegeServiceImpl(PrivilegeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Privilege> listAll(QueryCondition filter) {
        return repository.findAll();
    }

}
