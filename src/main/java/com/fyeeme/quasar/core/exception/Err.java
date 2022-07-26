package com.fyeeme.quasar.core.exception;

import org.springframework.http.HttpStatus;

public interface Err {

    /**
     * 获取错误编码
     *
     * @return
     */
    HttpStatus getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getMessage();
}
