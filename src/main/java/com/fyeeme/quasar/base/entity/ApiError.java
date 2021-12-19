package com.fyeeme.quasar.base.entity;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiError implements Serializable {
    public static final String FAIL = "fail";

    private Object data;
    private Integer code;
    private String status;
    private String message;
}
