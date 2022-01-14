package com.fyeeme.quasar.base.repository.dto;

import com.fyeeme.quasar.base.enums.FilterOperationEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FieldCondition {
    private String field;
    private FilterOperationEnum operator;
    private Object value;
}
