package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Exame;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The ExameDTO class represents a Data Transfer Object (DTO) for Exame entities.
 * It provides a simplified view of an Exame object for use in API responses.
 * This class implements the Serializable interface.
 */
public record ExameDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome,
        String categoria
) implements Serializable {

    /**
     * Creates a new ExameDTO instance based on the provided Exame object.
     *
     * @param exame The Exame object to convert to ExameDTO.
     * @return The ExameDTO representing the provided Exame object.
     */
    public static ExameDTO toDTO(Exame exame) {

        return new ExameDTO(
            exame.getId(),
            exame.getCreatedDate(),
            exame.getLastModifiedDate(),
            exame.getCreatedByUser(),
            exame.getModifiedByUser(),
            exame.getNome(),
            exame.getCategoria()
        );
    }
}