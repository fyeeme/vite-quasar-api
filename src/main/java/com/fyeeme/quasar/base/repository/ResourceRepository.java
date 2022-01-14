package com.fyeeme.quasar.base.repository;

import com.fyeeme.quasar.base.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResourceRepository<T extends BaseEntity> extends JpaRepository<T,Long>, JpaSpecificationExecutor<T> {
}
