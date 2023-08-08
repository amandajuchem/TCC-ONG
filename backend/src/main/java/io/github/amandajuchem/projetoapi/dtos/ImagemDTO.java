package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Imagem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ImagemDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome
) implements Serializable {

    public static ImagemDTO toDTO(Imagem imagem) {

        return new ImagemDTO(
                imagem.getId(),
                imagem.getCreatedDate(),
                imagem.getLastModifiedDate(),
                imagem.getCreatedByUser(),
                imagem.getModifiedByUser(),
                imagem.getNome()
        );
    }
}