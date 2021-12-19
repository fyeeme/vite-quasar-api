package com.fyeeme.quasar.configure.handler;

import com.fyeeme.quasar.base.entity.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.Objects;

@ControllerAdvice
public class UnifiedResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        // Only process the Class that has @RestController and return type is not null or String.
        var isRestController =
                returnType.getContainingClass().isAnnotationPresent(RestController.class);
        var resetMethod = returnType.getMethod();
        return isRestController && Objects.nonNull(resetMethod)
                && !resetMethod.getReturnType().isAssignableFrom(Void.TYPE)
                && !resetMethod.getReturnType().isAssignableFrom(String.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        return ApiResult.builder().code(HttpStatus.OK.value()).status(ApiResult.SUCCESS).data(body)
                .build();
    }

}
