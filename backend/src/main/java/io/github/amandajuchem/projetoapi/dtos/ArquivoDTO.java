package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Arquivo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ArquivoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome
) implements Serializable {

    public static ArquivoDTO toDTO(Arquivo imagem) {

        return new ArquivoDTO(
                imagem.getId(),
                imagem.getCreatedDate(),
                imagem.getLastModifiedDate(),
                imagem.getCreatedByUser(),
                imagem.getModifiedByUser(),
                imagem.getNome()
        );
    }
}