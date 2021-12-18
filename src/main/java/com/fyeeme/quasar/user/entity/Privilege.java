package com.fyeeme.quasar.user.entity;

import com.fyeeme.quasar.base.entity.BaseEntity;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Privilege extends BaseEntity {
    private String name;
    private String code;
    private String model;
    private String description;
}
