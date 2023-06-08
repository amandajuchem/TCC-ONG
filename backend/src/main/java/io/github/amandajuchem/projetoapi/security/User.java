package io.github.amandajuchem.projetoapi.security;

import jakarta.validation.constraints.NotEmpty;

public record User (
        @NotEmpty String username,
        @NotEmpty String password
) { }