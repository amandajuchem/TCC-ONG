package io.github.amandajuchem.projetoapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type Encode configuration.
 */
@Configuration
public class EncodeConfiguration {

    /**
     * Bcrypt password encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}