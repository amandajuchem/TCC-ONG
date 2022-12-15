package io.github.amandajuchem.projetoapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * The type Jwt utils.
 *
 * @author edson
 */
@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Generate token string.
     *
     * @param username the username
     * @return string string
     */
    public String generateToken (String username) {

        var key =  Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
        ;
    }

    /**
     *
     * @param token
     * @return
     */
    private Claims getClaims(String token) {

        var key =  Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets username.
     *
     * @param token the token
     * @return username username
     */
    public String getUsername(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
            return claims.getSubject();
        }

        return null;
    }

    /**
     * Validate token boolean.
     *
     * @param token the token
     * @return boolean boolean
     */
    public boolean validateToken(String token) {
        var claims = getClaims(token);

        if (claims != null) {
            var username = claims.getSubject();
            var expiration = claims.getExpiration();
            var now = new Date(System.currentTimeMillis());

            if (username != null) {

                if (expiration != null) {

                    if (now.before(expiration)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}