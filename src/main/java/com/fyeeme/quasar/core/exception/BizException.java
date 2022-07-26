package com.fyeeme.quasar.core.exception;

import com.fyeeme.quasar.base.entity.BaseEntity;
import com.fyeeme.quasar.base.util.CaseConverter;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3814545757153760732L;
    protected final HttpStatus code;
    protected final String message;

    public BizException(Err error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BizException(Err error, Class<? extends BaseEntity> clazz) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = String.format("%s%s", clazz.getSimpleName(), CaseConverter.convertToCamelCase(error.getMessage()));
    }

    public BizException(Err error, String message) {
        super(message);
        this.code = error.getCode();
        this.message = message;
    }

    public BizException(Err error, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
