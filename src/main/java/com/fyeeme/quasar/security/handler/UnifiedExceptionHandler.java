package com.fyeeme.quasar.security.handler;

import com.fyeeme.quasar.base.entity.ApiError;
import com.fyeeme.quasar.base.util.CaseConverter;
import com.fyeeme.quasar.security.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;


@Slf4j
@ControllerAdvice
public class UnifiedExceptionHandler {
    /**
     * 生产环境
     */
    private static final String ENV_PROD = "prod";

    @Autowired
    private Environment environment;

    /**
     * 业务异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<Object> handleBizException(BizException e) {
        var apiError = ApiError.builder().code(e.getStatus().value())
                .status(ApiError.FAIL).data(e.getCode()).message(e.getMessage())
                .build();
        return new ResponseEntity<>(apiError, e.getStatus());
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception e) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        var apiError = ApiError.builder().code(httpStatus.value())
                .status(ApiError.FAIL).data(CaseConverter.convertToCamelCase(httpStatus.getReasonPhrase()))
                .message(getErrorMessage(e)).build();
        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * Controller上一层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
            AccessDeniedException.class,
            AuthenticationException.class,
            AsyncRequestTimeoutException.class,

            BindException.class,

            ConversionNotSupportedException.class,

            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestPartException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,

            NoHandlerFoundException.class,

            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,

            TypeMismatchException.class,

            ServletRequestBindingException.class,
    })
//    @ResponseBody
    public ResponseEntity<Object> handleServletException(Exception e) {
        var errorResponse = ResponseEnum.valueOf(CaseConverter.convertToUpperSnakeCase(e.getClass().getSimpleName()));
        var httpStatus = errorResponse.getStatus();

        var apiError = ApiError.builder().code(httpStatus.value())
                .status(ApiError.FAIL).data(CaseConverter.convertToCamelCase(httpStatus.getReasonPhrase()))
                .message(getErrorMessage(e)).build();
        return new ResponseEntity<>(apiError, httpStatus);
    }


    private String getErrorMessage(Exception e) {
        log.info("Process error message for env: {} - {}", environment.getActiveProfiles(),
                e.getMessage());
        // only show detail msg in dev env.
        var data = e.getMessage();
        var isProdEnv = Arrays.asList(environment.getActiveProfiles()).contains(ENV_PROD);
        return isProdEnv ? e.getClass().getSimpleName() : data;
    }


    /**
     * @Description response error message code
     * @Author LiuYang
     * @Date 2021/3/10 5:33 PM
     * @Version V1.0
     */
    @Getter
    @AllArgsConstructor
    enum ResponseEnum {
        ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, ""),
        AUTHENTICATION_EXCEPTION(HttpStatus.UNAUTHORIZED, ""),
        ASYNC_REQUEST_TIMEOUT_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, ""),

        BIND_EXCEPTION(HttpStatus.BAD_REQUEST, ""),

        CONVERSION_NOT_SUPPORTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, ""),

        METHOD_ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, ""),
        METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, ""),
        MISSING_PATH_VARIABLE_EXCEPTION(HttpStatus.BAD_REQUEST, ""),
        MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, ""),
        MISSING_SERVLET_REQUEST_PART_EXCEPTION(HttpStatus.BAD_REQUEST, ""),

        NO_HANDLER_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, ""),

        HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(HttpStatus.METHOD_NOT_ALLOWED, ""),
        HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ""),
        HTTP_MESSAGE_NOT_READABLE_EXCEPTION(HttpStatus.BAD_REQUEST, ""),
        HTTP_MESSAGE_NOT_WRITABLE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, ""),
        HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION(HttpStatus.NOT_ACCEPTABLE, ""),

        TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, ""),

        SERVLET_REQUEST_BINDING_EXCEPTION(HttpStatus.BAD_REQUEST, "");
        /**
         * HTTP状态码
         */
        private HttpStatus status;
        /**
         * 返回信息，直接读取异常的message
         */
        private String message;

    }
}
