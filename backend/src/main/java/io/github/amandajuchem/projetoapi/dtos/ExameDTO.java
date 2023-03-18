package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Exame;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Exame dto.
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
     * To dto exame dto.
     *
     * @param exame the exame
     * @return the exame dto
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