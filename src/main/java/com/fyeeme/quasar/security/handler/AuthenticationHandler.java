package com.fyeeme.quasar.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyeeme.quasar.base.entity.ApiError;
import com.fyeeme.quasar.base.entity.ApiResult;
import com.fyeeme.quasar.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    public AuthenticationHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        var principal = (User) authentication.getPrincipal();

        var apiResult = ApiResult.of(principal);


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().append(objectMapper.writeValueAsString(apiResult));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        //状态OK, 数据提示登陆失败
        var apiResult = ApiError.of(exception.getClass().getSimpleName(), "400", exception.getMessage());
        log.error("Login failed: {}", exception.getMessage(), exception);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().append(objectMapper.writeValueAsString(apiResult));
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        var apiResult = ApiResult.of(true);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().append(objectMapper.writeValueAsString(apiResult));
    }
}
