package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.FichaMedica;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record FichaMedicaDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotEmpty String comorbidades
) implements Serializable {

    public static FichaMedicaDTO toDTO (FichaMedica fichaMedica) {

        return new FichaMedicaDTO(
            fichaMedica.getId(),
            fichaMedica.getCreatedDate(),
            fichaMedica.getLastModifiedDate(),
            fichaMedica.getCreatedByUser(),
            fichaMedica.getModifiedByUser(),
            fichaMedica.getComorbidades()
        );
    }
}