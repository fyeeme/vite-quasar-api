package com.fyeeme.quasar.configure.jpa;

import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(1L);
        // Can use Spring Security to return currently logged in user

        // return Optional.ofNullable(SecurityContextHolder.getContext())
        // .map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated)
        // .map(Authentication::getPrincipal).map(User.class::cast);
    }

}
