package com.fyeeme.quasar.security.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3814545757153760732L;
    protected final String code;
    protected final HttpStatus status;

    public BizException(ErrorCode error) {
        super(error.getCode());
        this.code = error.getCode();
        this.status = error.getStatus();
    }

    public BizException(ErrorCode error, String message) {
        super(message);
        this.code = error.getCode();
        this.status = error.getStatus();
    }

    public BizException(ErrorCode error, Throwable throwable) {
        super(error.getCode(), throwable);
        this.code = error.getCode();
        this.status = error.getStatus();
    }
}
