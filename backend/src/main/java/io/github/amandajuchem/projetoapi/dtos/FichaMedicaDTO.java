package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.FichaMedica;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The FichaMedicaDTO class represents a Data Transfer Object (DTO) for FichaMedica entities.
 * It provides a simplified view of a FichaMedica object for use in API responses.
 * This class implements the Serializable interface.
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
     * Creates a new FichaMedicaDTO instance based on the provided FichaMedica object.
     *
     * @param fichaMedica The FichaMedica object to convert to FichaMedicaDTO.
     * @return The FichaMedicaDTO representing the provided FichaMedica object.
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