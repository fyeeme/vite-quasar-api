package com.fyeeme.quasar.core.handler;

import com.fyeeme.quasar.base.entity.ApiError;
import com.fyeeme.quasar.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;

/**
 * https://devwithus.com/exception-handling-spring-boot-rest-api/
 */
@Slf4j
@ControllerAdvice
public class UnifiedExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 生产环境
     */
    private static final String ENV_PROD = "prod";

    @Autowired
    private Environment environment;

    /**
     * 业务异常
     *
     * @param ex 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<Object> handleBizException(BizException ex, WebRequest request) {
        return buildResponseEntity(ex, ex.getMessage(), ex.getCode());
    }

    /**
     * 未定义异常
     *
     * @param ex 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof AccessDeniedException) {
            httpStatus = HttpStatus.FORBIDDEN;
        }
        return buildResponseEntity(ex, httpStatus.name(), String.valueOf(httpStatus.value()));
    }

    /**
     * rewrite default exception
     * AccessDeniedException.class,
     * AuthenticationException.class,
     * AsyncRequestTimeoutException.class,
     * <p>
     * BindException.class,
     * <p>
     * ConversionNotSupportedException.class,
     * <p>
     * MethodArgumentNotValidException.class,
     * MethodArgumentTypeMismatchException.class,
     * MissingServletRequestPartException.class,
     * MissingPathVariableException.class,
     * MissingServletRequestParameterException.class,
     * <p>
     * NoHandlerFoundException.class,
     * <p>
     * HttpRequestMethodNotSupportedException.class,
     * HttpMediaTypeNotSupportedException.class,
     * HttpMessageNotReadableException.class,
     * HttpMessageNotWritableException.class,
     * HttpMediaTypeNotAcceptableException.class,
     * <p>
     * TypeMismatchException.class,
     * <p>
     * ServletRequestBindingException.class,
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return buildResponseEntity(ex, status.getReasonPhrase(), String.valueOf(status.value()));
    }


    private String getErrorMessage(Exception ex) {
        log.error("Process error message for env: {} - {}", environment.getActiveProfiles(), ex.getMessage(), ex);
        // only show detail msg in dev env.
        var data = ex.getLocalizedMessage();
        var isProdEnv = Arrays.asList(environment.getActiveProfiles()).contains(ENV_PROD);
        return isProdEnv ? ex.getClass().getSimpleName() : data;
    }

    private ResponseEntity<Object> buildResponseEntity(Exception ex, String message, String code) {
        return new ResponseEntity<>(ApiError.of(getErrorMessage(ex), message, code), HttpStatus.OK);
    }
}
