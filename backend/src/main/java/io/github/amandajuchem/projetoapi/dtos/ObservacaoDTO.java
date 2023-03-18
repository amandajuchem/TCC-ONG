package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Observacao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Observacao dto.
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
     * To dto observacao dto.
     *
     * @param observacao the observacao
     * @return the observacao dto
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