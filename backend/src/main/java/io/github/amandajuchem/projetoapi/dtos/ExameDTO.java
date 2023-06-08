package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Exame;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExameDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome,
        String categoria
) implements Serializable {

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