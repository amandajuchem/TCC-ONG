package io.github.amandajuchem.projetoapi.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Utility class for JWT (JSON Web Token) token generation.
 */
@Component
@RequiredArgsConstructor
public class JWTTokenUtils {

    private final JwtEncoder encoder;

    /**
     * Generates a JWT token based on the provided authentication object.
     *
     * @param authentication the authentication object representing the user's authentication details.
     * @return the generated JWT token.
     */
    public String generateToken(Authentication authentication) {

        final var now = Instant.now();
        final var scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        final var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(12, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}