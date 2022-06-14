package com.fyeeme.quasar.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum FilterOperationEnum {

    EQUAL("eq"),
    LIKE("like"),

    NOT_EQUAL("neq"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL_TO("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL_TO("lte"),
    IN("in"),
    NOT_IN("nin"),
    BETWEEN("btn"),
    CONTAINS("like"),
    NOT_CONTAINS("notLike"),
    IS_NULL("isnull"),
    IS_NOT_NULL("isNotNull"),
    START_WITH("startWith"),
    END_WITH("endWith"),
    IS_EMPTY("isempty"),
    IS_NOT_EMPTY("isNotEmpty"),
    JOIN("jn"),
    IS("is");

    private final String value;
    //缓存 values
    static final FilterOperationEnum[] VALUES;
    static  {
        VALUES = FilterOperationEnum.values();
    }
    public static FilterOperationEnum fromValue(String value) {
        return Arrays.stream(VALUES)
                .filter(item -> item.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return value;
    }
}
