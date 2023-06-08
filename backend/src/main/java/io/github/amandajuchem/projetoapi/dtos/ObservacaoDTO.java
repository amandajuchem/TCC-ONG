package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Observacao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ObservacaoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String conteudo
) implements Serializable {

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