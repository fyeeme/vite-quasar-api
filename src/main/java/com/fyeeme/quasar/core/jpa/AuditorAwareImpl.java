package com.fyeeme.quasar.core.jpa;

import com.fyeeme.quasar.user.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        var currentUser = (User) authentication.getPrincipal();
        return Optional.of(currentUser.getId());
    }

}
