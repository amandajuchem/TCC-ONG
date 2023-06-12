package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Observacao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The ObservacaoDTO class represents a Data Transfer Object (DTO) for Observacao entities.
 * It provides a simplified view of an Observacao object for use in API responses.
 * This class implements the Serializable interface.
 */
public record ObservacaoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String conteudo
) implements Serializable {

    /**
     * Creates a new ObservacaoDTO instance based on the provided Observacao object.
     *
     * @param observacao The Observacao object to convert to ObservacaoDTO.
     * @return The ObservacaoDTO representing the provided Observacao object.
     */
    public static ObservacaoDTO toDTO(Observacao observacao) {

        return new ObservacaoDTO(
            observacao.getId(),
            observacao.getCreatedDate(),
            observacao.getLastModifiedDate(),
            observacao.getCreatedByUser(),
            observacao.getModifiedByUser(),
            observacao.getConteudo()
        );
    }
}