package com.fyeeme.quasar.configure.handler;

import com.fyeeme.quasar.base.entity.ApiError;
import com.fyeeme.quasar.base.entity.ApiError;
import com.fyeeme.quasar.configure.exception.BizException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
import javax.servlet.http.HttpServletResponse;
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
    public ApiError handleBizException(BizException e) {
        String message = getErrorMessage(e);
        return ApiError.builder().code(e.getStatus().value()).status(ApiError.FAIL).message(message)
                .build();
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiError handleException(Exception e) {
        String message = getErrorMessage(e);
        return ApiError.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(ApiError.FAIL).message(message).build();
    }

    /**
     * Controller上一层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
        // @formatter:off
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
    //@formatter:on
    @ResponseBody
    public ApiError handleServletException(Exception e) {
        var errorResponse = ResponseEnum.valueOf(e.getClass().getSimpleName());
        var code = errorResponse.getCode();
        String message = getErrorMessage(e);

        return ApiError.builder().code(code).status(ApiError.FAIL).message(message).build();
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
     * @Description Sevlet response error message code
     * @Author LiuYang
     * @Date 2021/3/10 5:33 PM
     * @Version V1.0
     */
    @Getter
    @AllArgsConstructor
    enum ResponseEnum {
        // @formatter:off
        AccessDeniedException(HttpServletResponse.SC_FORBIDDEN, ""),
        AuthenticationException(HttpServletResponse.SC_UNAUTHORIZED, ""),
        AsyncRequestTimeoutException(HttpServletResponse.SC_SERVICE_UNAVAILABLE, ""),

        BindException(HttpServletResponse.SC_BAD_REQUEST, ""),

        ConversionNotSupportedException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ""),

        MethodArgumentNotValidException(HttpServletResponse.SC_BAD_REQUEST, ""),
        MethodArgumentTypeMismatchException(HttpServletResponse.SC_BAD_REQUEST, ""),
        MissingPathVariableException(HttpServletResponse.SC_BAD_REQUEST, ""),
        MissingServletRequestParameterException(HttpServletResponse.SC_BAD_REQUEST, ""),
        MissingServletRequestPartException(HttpServletResponse.SC_BAD_REQUEST, ""),

        NoHandlerFoundException(HttpServletResponse.SC_NOT_FOUND, ""),

        HttpRequestMethodNotSupportedException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ""),
        HttpMediaTypeNotSupportedException(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, ""),
        HttpMessageNotReadableException(HttpServletResponse.SC_BAD_REQUEST, ""),
        HttpMessageNotWritableException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ""),
        HttpMediaTypeNotAcceptableException(HttpServletResponse.SC_NOT_ACCEPTABLE, ""),

        TypeMismatchException(HttpServletResponse.SC_BAD_REQUEST, ""),

        ServletRequestBindingException(HttpServletResponse.SC_BAD_REQUEST, "");;
        //@formatter:on
        /**
         * HTTP状态码
         */
        private int code;
        /**
         * 返回信息，直接读取异常的message
         */
        private String message;

    }
}
