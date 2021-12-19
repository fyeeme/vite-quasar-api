package com.fyeeme.quasar.base.entity;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResult implements Serializable {
    public static final String SUCCESS = "success";

    private Integer code;
    private String status;
    private Object data;
}
