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
    private String code;
    private String status;
    private String message;

    private ApiError(T data, String message, String code) {
        this.data = data;
        this.status = FAIL;
        this.code = code;
        this.message = message;
    }

    public static <T> ApiError<T> of(T data, String message, String code) {
        return new ApiError<>(data, message, code);
    }
}
