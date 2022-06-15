package com.fyeeme.quasar.security.configurer;

import com.fyeeme.quasar.security.handler.AuthenticationHandler;
import com.fyeeme.quasar.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final AuthenticationHandler authenticationHandler;
    private final UserService userService;

    public SecurityConfig(AuthenticationHandler authenticationHandler, UserService userService) {
        this.authenticationHandler = authenticationHandler;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // by default uses a Bean by the name of corsConfigurationSource
        return http.csrf().disable()
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .formLogin(formLoginCustomizer -> {
                    formLoginCustomizer.successHandler(authenticationHandler);
                    formLoginCustomizer.failureHandler(authenticationHandler);
                })
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessHandler(authenticationHandler))
                .build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getByUsername;
    }


    @Bean
    PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * use global cors replace @CrossOrigin on Controller level
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3030","http://localhost:3010","http://localhost:4173"));//
        configuration.setAllowedMethods(Arrays.asList("GET", "POST","PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setMaxAge(Duration.ofMinutes(10));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
//    @Bean
//    AuthenticationSuccessHandler successHandler() {
//        return authenticationHandler;
//    }
//
//    @Bean
//    AuthenticationFailureHandler failureHandler() {
//        return authenticationHandler;
//    }
//
//    @Bean
//    LogoutSuccessHandler logoutHandler() {
//        return authenticationHandler;
//    }


}
