package com.fyeeme.quasar.configure.exception;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // AccessDeniedException(HttpStatus.FORBIDDEN, "FORBIDDEN"),
    // AuthenticationException(HttpStatus.UNAUTHORIZED, "FORBIDDEN");

    // authenticate
    // @formatter:off
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,  "Unauthorized"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),

    // custom code should be 400 or 404
    USER_DISABLED(HttpStatus.BAD_REQUEST,  "User not found"),
    MEMBER_NOT_EXISTED(HttpStatus.FORBIDDEN, "Member not existed")
    ;

    private HttpStatus status;
    private String code;
}
