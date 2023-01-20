package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Imagem;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Imagem dto.
 */
public record ImagemDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotEmpty String nome
) implements Serializable {

    /**
     * To dto imagem dto.
     *
     * @param imagem the imagem
     * @return the imagem dto
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