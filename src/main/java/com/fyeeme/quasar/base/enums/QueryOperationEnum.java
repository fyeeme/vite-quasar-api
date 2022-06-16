package com.fyeeme.quasar.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum QueryOperationEnum {

    EQUAL("eq"),
    LIKE("like"),

    NOT_EQUAL("not_eq"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL_TO("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL_TO("lte"),
    IN("in"),
    NOT_IN("not_in"),
    BETWEEN("bw"),
    CONTAINS("like"),
    NOT_CONTAINS("not_like"),
    IS_NULL("is_null"),
    IS_NOT_NULL("not_null"),
    START_WITH("start_with"),
    END_WITH("end_with"),
    IS_EMPTY("is_empty"),
    IS_NOT_EMPTY("not_empty"),
    JOIN("jn"),
    IS("is");

    private final String value;
    //缓存 values
    static final QueryOperationEnum[] VALUES;
    static  {
        VALUES = QueryOperationEnum.values();
    }
    public static QueryOperationEnum fromValue(String value) {
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
