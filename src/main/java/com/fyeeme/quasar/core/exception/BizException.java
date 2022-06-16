package com.fyeeme.quasar.core.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3814545757153760732L;
    protected final String code;
    protected final String message;
    protected final String status;

    public BizException(Err error) {
        super(error.getCode());
        this.code = error.getCode();
        this.status = error.getCode();
        this.message = error.getMessage();
    }

    public BizException(Err error, String message) {
        super(message);
        this.code = error.getCode();
        this.status = error.getCode();
        this.message = message;
    }

    public BizException(Err error, Throwable throwable) {
        super(error.getCode(), throwable);
        this.code = error.getCode();
        this.status = error.getCode();
        this.message = error.getMessage();
    }

    public BizException(Err module, Err error) {
        super(module.getMessage() + error.getMessage());
        this.code = module.getCode() + error.getCode();
        this.status = error.getCode();
        this.message = module.getMessage() + error.getMessage();
    }
}
