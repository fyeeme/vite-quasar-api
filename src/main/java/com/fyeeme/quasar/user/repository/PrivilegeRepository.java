package com.fyeeme.quasar.user.repository;

import com.fyeeme.quasar.user.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
