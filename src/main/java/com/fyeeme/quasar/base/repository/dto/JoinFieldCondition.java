package com.fyeeme.quasar.base.repository.dto;

import lombok.Data;

@Data
public class JoinFieldCondition {
    private String joinField;
    private FieldCondition condition;
}
