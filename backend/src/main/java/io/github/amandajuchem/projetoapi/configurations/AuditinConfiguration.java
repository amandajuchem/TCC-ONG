package io.github.amandajuchem.projetoapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * The type Auditin configuration.
 */
@Configuration
@EnableJpaAuditing
public class AuditinConfiguration {

    /**
     * Auditor provider auditor aware.
     *
     * @return auditor aware
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    private class AuditorAwareImpl implements AuditorAware<String> {


        @Override
        public Optional<String> getCurrentAuditor() {

            return Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getName)
                    ;
        }
    }
}