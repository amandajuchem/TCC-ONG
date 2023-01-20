package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Castracao;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Castracao dto.
 */
public record CastracaoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotNull LocalDateTime dataHora,
        AnimalDTO animal,
        UsuarioDTO usuario
) implements Serializable {

    /**
     * To dto castracao dto.
     *
     * @param castracao the castracao
     * @return the castracao dto
     */
    public static CastracaoDTO toDTO(Castracao castracao) {

        return new CastracaoDTO(
                castracao.getId(),
                castracao.getCreatedDate(),
                castracao.getLastModifiedDate(),
                castracao.getCreatedByUser(),
                castracao.getModifiedByUser(),
                castracao.getDataHora(),
                castracao.getAnimal() != null ? AnimalDTO.toDTO(castracao.getAnimal()) : null,
                castracao.getUsuario() != null ? UsuarioDTO.toDTO(castracao.getUsuario()) : null
        );
    }
}