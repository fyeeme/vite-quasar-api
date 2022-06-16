package com.fyeeme.quasar.runner;

import com.fyeeme.quasar.core.property.QuasarProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@EnableConfigurationProperties(QuasarProperties.class)
@Component
public class ConfigCommandRunner implements CommandLineRunner {
    @Autowired
    QuasarProperties quasarProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("config's url is :{}, name:{}", quasarProperties.getUrl(), quasarProperties.getSecurity().getUsername());
    }
}
