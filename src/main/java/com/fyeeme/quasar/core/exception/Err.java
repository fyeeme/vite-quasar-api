package com.fyeeme.quasar.core.exception;

public interface Err {

    /**
     * 获取错误编码
     *
     * @return
     */
    String getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getMessage();
}
