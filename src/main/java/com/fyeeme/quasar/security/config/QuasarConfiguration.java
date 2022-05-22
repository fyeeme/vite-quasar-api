package com.fyeeme.quasar.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(QuasarProperties.class)
public class QuasarConfiguration {
    @Autowired
    QuasarProperties quasarProperties;

}
