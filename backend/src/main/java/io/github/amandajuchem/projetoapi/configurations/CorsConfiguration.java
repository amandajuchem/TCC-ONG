package io.github.amandajuchem.projetoapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS).
 */
@Configuration
public class CorsConfiguration {

    /**
     * Creates a CorsConfigurationSource bean with configured CORS settings.
     *
     * @return the CorsConfigurationSource instance
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        final var corsConfiguration = new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues();
        final var source = new UrlBasedCorsConfigurationSource();

        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}