package io.github.amandajuchem.projetoapi.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public record Authentication(
        @NotEmpty @JsonProperty("access_token") String accessToken
) { }