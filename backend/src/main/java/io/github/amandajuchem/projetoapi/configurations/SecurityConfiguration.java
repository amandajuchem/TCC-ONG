package io.github.amandajuchem.projetoapi.configurations;

import io.github.amandajuchem.projetoapi.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * The type Security configuration.
 */
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final CorsConfigurationSource corsConfigurationSource;
    private final JWTUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    private static final String[] PUBLIC_MATCHERS_GET = {
        "/imagens/*"
    };

    /**
     * Instantiates a new Security configuration.
     *
     * @param corsConfigurationSource the cors configuration source
     * @param jwtUtils                the jwt utils
     * @param userDetailsService      the user details service
     */
    @Autowired
    public SecurityConfiguration(CorsConfigurationSource corsConfigurationSource, JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
            .anyRequest().authenticated()
        ;

        http.apply(new HttpConfigurer(jwtUtils, userDetailsService));
        http.cors().configurationSource(corsConfigurationSource);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}