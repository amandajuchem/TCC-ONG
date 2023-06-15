package io.github.amandajuchem.projetoapi.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation.
 */
@Configuration
public class OpenAPIConfiguration {

    /**
     * Creates and configures the OpenAPI object.
     *
     * @return the OpenAPI instance
     */
    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("ProjetoAPI")
                        .description("Ferramenta para o gerenciamento de animais e do processo de adoção em ONGS.")
                );
    }
}