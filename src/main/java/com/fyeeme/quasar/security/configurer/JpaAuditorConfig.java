package com.fyeeme.quasar.security.configurer;

import com.fyeeme.quasar.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

@Configuration
// @EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaAuditing
public class JpaAuditorConfig {
    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAware<Long>() {
            @Override
            public Optional<Long> getCurrentAuditor() {
                var authentication = SecurityContextHolder.getContext().getAuthentication();

                if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
                    return Optional.empty();
                }
                var currentUser = (User) authentication.getPrincipal();
                return Optional.of(currentUser.getId());
            }
        };
    }
}
