package com.fyeeme.quasar.base.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ApiResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3730196843273566375L;
    public static final String SUCCESS = "success";
    public static final String CODE = "200";

    private T data;
    private String code;
    private String status;

    private ApiResult(T data) {
        this.data = data;
        this.code = CODE;
        this.status = SUCCESS;
    }

    public static <T> ApiResult<T> of(T data) {
        return new ApiResult<>(data);
    }

}
