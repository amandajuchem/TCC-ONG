package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Agendamento;

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
     * @param agendamento the castracao
     * @return the castracao dto
     */
    public static CastracaoDTO toDTO(Agendamento agendamento) {

        return new CastracaoDTO(
                agendamento.getId(),
                agendamento.getCreatedDate(),
                agendamento.getLastModifiedDate(),
                agendamento.getCreatedByUser(),
                agendamento.getModifiedByUser(),
                agendamento.getDataHora(),
                agendamento.getAnimal() != null ? AnimalDTO.toDTO(agendamento.getAnimal()) : null,
                agendamento.getUsuario() != null ? UsuarioDTO.toDTO(agendamento.getUsuario()) : null
        );
    }
}