package com.fyeeme.quasar.base.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class ApiResult implements Serializable {
    public static final String SUCCESS = "success";
    @Serial
    private static final long serialVersionUID = 3730196843273566375L;

    private Integer code;
    private String status;
    private Object data;
}
