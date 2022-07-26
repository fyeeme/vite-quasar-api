package com.fyeeme.quasar.core.exception;

import com.fyeeme.quasar.base.entity.BaseEntity;
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
     * @param object 需要检查的对象
     * @param err    错误信息
     */
    public static void notNull(Object object, Err err) {
        if (object == null) {
            throw new BizException(err);
        }
    }


    public static void notNull(Object object, Err err, String message) {
        if (object == null) {
            throw new BizException(err, message);
        }
    }

    public static void notNull(Object object, Class<? extends BaseEntity> clazz, Err err) {
        if (object == null) {
            throw new BizException(err, clazz);
        }
    }

    /**
     * 如果当前对象为空，则抛出指定的错误信息
     *
     * @param collection 需要检查的对象
     * @param err        错误信息
     */
    public static void notEmpty(Collection<?> collection, Err err) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(err);
        }
    }

    public static void notEmpty(Collection<?> collection, Class<? extends BaseEntity> clazz, Err err) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(err, clazz);
        }
    }

    /**
     * 如果当前字符串没有内容，则抛出指定的错误信息
     *
     * @param text 需要检查的对象
     * @param err  错误信息
     */
    public static void hasText(String text, Err err) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(err);
        }
    }

    public static void hasText(String text, Err err, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(err, message);
        }
    }

    /**
     * 如果当前表达式不成立，则抛出指定的错误信息
     *
     * @param expression 需要检查的对象
     * @param err        错误信息
     */
    public static void isTrue(boolean expression, Err err) {
        if (!expression) {
            throw new BizException(err);
        }
    }

    public static void isTrue(boolean expression, Class<? extends BaseEntity> clazz, Err err) {
        if (!expression) {
            throw new BizException(err, clazz);
        }
    }

    public static void isTrue(boolean expression, Err err, String message) {
        if (!expression) {
            throw new BizException(err, message);
        }
    }
}
