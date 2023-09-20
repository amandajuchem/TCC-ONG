package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.ExameRealizado;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExameRealizadoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        ExameDTO exame,
        ImagemDTO imagem
) implements Serializable {

    public static ExameRealizadoDTO toDTO(ExameRealizado exameRealizado) {

        return new ExameRealizadoDTO(
                exameRealizado.getId(),
                exameRealizado.getCreatedDate(),
                exameRealizado.getLastModifiedDate(),
                exameRealizado.getCreatedByUser(),
                exameRealizado.getModifiedByUser(),
                exameRealizado.getExame() != null ? ExameDTO.toDTO(exameRealizado.getExame()) : null,
                exameRealizado.getImagem() != null ? ImagemDTO.toDTO(exameRealizado.getImagem()) : null
        );
    }
}