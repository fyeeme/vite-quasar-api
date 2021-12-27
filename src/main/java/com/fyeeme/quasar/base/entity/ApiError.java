package com.fyeeme.quasar.base.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class ApiError implements Serializable {
    public static final String FAIL = "fail";
    @Serial
    private static final long serialVersionUID = -3585800028253838082L;

    private Integer code;
    private String status;
    private Object data;
    private String message;
}
