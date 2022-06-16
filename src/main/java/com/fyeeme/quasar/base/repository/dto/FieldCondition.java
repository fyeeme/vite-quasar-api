package com.fyeeme.quasar.base.repository.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class FieldCondition {
    private String field;
    private String operator;
    private Object value;
}
