package io.github.amandajuchem.projetoapi.filters;

import io.github.amandajuchem.projetoapi.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Authorization filter.
 *
 * @author edson
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * Instantiates a new Authorization filter.
     *
     * @param authenticationManager the authentication manager
     * @param jwtUtils              the jwt utils
     * @param userDetailsService    the user details service
     */
    public AuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Do filter internal.
     *
     * @param request  the request
     * @param response the response
     * @param chain    the chain
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        var authorization = request.getHeader("Authorization");

        if (authorization != null) {

            if (authorization.startsWith("Bearer ")) {

                var auth = getAuthentication(authorization.substring(7));

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if (jwtUtils.validateToken(token)) {
            var username = jwtUtils.getUsername(token);
            var user = userDetailsService.loadUserByUsername(username);

            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }

        return null;
    }
}