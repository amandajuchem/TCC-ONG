package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.FichaMedica;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Ficha medica dto.
 */
public record FichaMedicaDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String comorbidades,
        Boolean castrado
) implements Serializable {

    /**
     * To ficha medica dto.
     *
     * @param fichaMedica the ficha medica
     * @return the ficha medica dto
     */
    public static FichaMedicaDTO toDTO(FichaMedica fichaMedica) {

        return new FichaMedicaDTO(
                fichaMedica.getId(),
                fichaMedica.getCreatedDate(),
                fichaMedica.getLastModifiedDate(),
                fichaMedica.getCreatedByUser(),
                fichaMedica.getModifiedByUser(),
                fichaMedica.getComorbidades(),
                fichaMedica.getCastrado()
        );
    }
}