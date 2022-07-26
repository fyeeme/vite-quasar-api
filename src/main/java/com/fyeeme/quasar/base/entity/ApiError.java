package com.fyeeme.quasar.base.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError<T> {
    private static final String FAIL = "fail";
    // TODO if error occurred when validate entity, errors may be list.
    // private List<String> data;
    private T data;
    private Integer code;
    private String status;

    private String message;

    private ApiError(T data, String message, Integer code) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.status = FAIL;
    }

    public static <T> ApiError<T> of(T data, String message, Integer code) {
        return new ApiError<>(data, message, code);
    }
}
