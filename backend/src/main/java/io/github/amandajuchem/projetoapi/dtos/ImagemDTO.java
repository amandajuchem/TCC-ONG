package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Imagem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The ImagemDTO class represents a Data Transfer Object (DTO) for Imagem entities.
 * It provides a simplified view of an Imagem object for use in API responses.
 * This class implements the Serializable interface.
 */
public record ImagemDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome
) implements Serializable {

    /**
     * Creates a new ImagemDTO instance based on the provided Imagem object.
     *
     * @param imagem The Imagem object to convert to ImagemDTO.
     * @return The ImagemDTO representing the provided Imagem object.
     */
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