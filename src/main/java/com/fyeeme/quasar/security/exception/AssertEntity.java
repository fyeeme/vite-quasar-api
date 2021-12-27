package com.fyeeme.quasar.security.exception;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @Description 简化异常使用的工具类
 * @Author LiuYang
 * @Email lorraine@petalmail.com
 * @Date 2021/3/10 3:09 PM
 * @Version V1.0
 */
public class AssertEntity {
    private AssertEntity() {
    }

    /**
     * 如果当前对象为空，则抛出指定的错误信息
     *
     * @param object    需要检查的对象
     * @param errorCode 错误信息
     */
    public static void notNull(Object object, ErrorCode errorCode) {
        if (object == null) {
            throw new BizException(errorCode);
        }
    }


    public static void notNull(Object object, ErrorCode errorCode, String message) {
        if (object == null) {
            throw new BizException(errorCode, message);
        }
    }

    /**
     * 如果当前对象为空，则抛出指定的错误信息
     *
     * @param collection 需要检查的对象
     * @param errorCode  错误信息
     */
    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(errorCode);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(errorCode, message);
        }
    }

    /**
     * 如果当前字符串没有内容，则抛出指定的错误信息
     *
     * @param text      需要检查的对象
     * @param errorCode 错误信息
     */
    public static void hasText(String text, ErrorCode errorCode) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(errorCode);
        }
    }

    public static void hasText(String text, ErrorCode errorCode, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(errorCode, message);
        }
    }

    /**
     * 如果当前表达式不成立，则抛出指定的错误信息
     *
     * @param expression 需要检查的对象
     * @param errorCode  错误信息
     */
    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BizException(errorCode);
        }
    }

    public static void isTrue(String text, ErrorCode errorCode, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(errorCode, message);
        }
    }
}
