package com.fyeeme.quasar.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Exception 的 status 根据状态码确定，总共可改概括为一下几种
 * 200 - 请求成功，response body 为返回的业务数据，无任何其它包装（无status、code、data等）
 * <p>
 * 400 - 请求参数错误，例如创建课程时，请求参数中课程名称为空。
 * 401 - 未登录，所请求的资源需要登录后才能访问，例如未登录用户访问“我的在学课程”接口。
 * 403 - 无权限，当前用户已登陆，但无权限访问所请求的资源，例如学生用户试图删除某个课程。
 * 404 - 资源不存在，所请求的URL或资源不存在，例如获取课程详情时，所传入的课程UID错误。
 * 409 - 资源冲突，所请求的资源与已存在的资源冲突，例如创建课程分类时，分类代码已存在。
 * 429 - 请求超出上限，例如报名线下活动的人数超过限制人数。
 * <p>
 * 500 - 服务内部错误，代码中未捕获或未进行特殊处理，例如由于手工删除数据库中数据造成代码错误。
 * 503 - 服务不可用，例如所访问网校已停止服务。
 * <p>
 * Code 各式
 * INVALID_REQUEST https://developer.paypal.com/api/rest/responses/
 * invalidRequest https://cloud.google.com/storage/docs/json_api/v1/status-codes#409-conflict
 * InvalidRequest https://help.aliyun.com/document_detail/31973.html?spm=a2c4g.11186623.6.1609.23975c9cjCf71z
 */
@Getter
@AllArgsConstructor
public enum CommonError implements Err {

    //common
    BAD_REQUEST("400", " Bad Request"),
    UNAUTHORIZED("401", " Unauthorized"),
    FORBIDDEN("403", " Forbidden"),
    NOT_FOUND("404", " Not Found"),
    CONFLICT("409", " Conflict"),
    TOO_MANY_REQUESTS("429", " Too Many Requests"),
    UNDEFINED("500", " Internal Server Error"),

    //business related more detailed for    CONFLICT("409", "Conflict")
    ALREADY_JOINED("409", " Already Joined"),
    NOT_STARTED("409", " Not Start"),
    ALREADY_FINISHED("409", " Already Finished"),
    DISABLED("403", " Disabled"),


    //Module
    USER("100", "User"),
    ROLE("101", "Role"),
    PRIVILEGE("102", "Privilege"),

    COURSE("200", "Course"),
    COURSE_CATEGORY("201", "Course Category")
    // 400
    // 100400
    // USER BAD REQUEST

    //newly design
    ;

    private String code;
    private String message;


}
