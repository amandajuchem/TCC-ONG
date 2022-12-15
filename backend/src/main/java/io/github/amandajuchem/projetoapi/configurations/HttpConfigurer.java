package io.github.amandajuchem.projetoapi.configurations;

import io.github.amandajuchem.projetoapi.filters.AuthenticationFilter;
import io.github.amandajuchem.projetoapi.filters.AuthorizationFilter;
import io.github.amandajuchem.projetoapi.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The type Http configurer.
 *
 * @author edson
 */
public class HttpConfigurer extends AbstractHttpConfigurer<HttpConfigurer, HttpSecurity> {

    private final JWTUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * Instantiates a new Http configurer.
     *
     * @param jwtUtils           the jwt utils
     * @param userDetailsService the user details service
     */
    public HttpConfigurer(JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) {
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http.addFilter(new AuthenticationFilter(authenticationManager, jwtUtils));
        http.addFilter(new AuthorizationFilter(authenticationManager, jwtUtils, userDetailsService));
    }
}