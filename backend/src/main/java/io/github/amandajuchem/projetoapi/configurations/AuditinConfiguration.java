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
 * Configuration class for auditing, enabling JPA auditing.
 */
@Configuration
@EnableJpaAuditing
public class AuditinConfiguration {

    /**
     * Provides the auditor for the auditing process.
     *
     * @return the AuditorAware implementation
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    /**
     * Implementation of the AuditorAware interface to retrieve the current auditor.
     */
    private static class AuditorAwareImpl implements AuditorAware<String> {

        /**
         * Retrieves the current auditor.
         *
         * @return an optional containing the current auditor's name if available, empty otherwise
         */
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