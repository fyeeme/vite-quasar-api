package com.fyeeme.quasar.user.entity;

import com.fyeeme.quasar.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
public class Privilege extends BaseEntity {
    private String name;
    private String code;
    private String model;
    private String description;
}
