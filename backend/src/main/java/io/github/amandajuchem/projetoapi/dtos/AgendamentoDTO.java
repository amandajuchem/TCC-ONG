package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Agendamento;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Agendamento dto.
 */
public record AgendamentoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotNull LocalDateTime dataHora,
        @NotEmpty String descricao,
        AnimalDTO animal,
        UsuarioDTO veterinario
) implements Serializable {

    /**
     * To agendamento dto.
     *
     * @param agendamento the agendamento
     * @return the agendamento dto
     */
    public static AgendamentoDTO toDTO(Agendamento agendamento) {

        return new AgendamentoDTO(
            agendamento.getId(),
            agendamento.getCreatedDate(),
            agendamento.getLastModifiedDate(),
            agendamento.getCreatedByUser(),
            agendamento.getModifiedByUser(),
            agendamento.getDataHora(),
            agendamento.getDescricao(),
            agendamento.getAnimal() != null ? AnimalDTO.toDTO(agendamento.getAnimal()) : null,
            agendamento.getVeterinario() != null ? UsuarioDTO.toDTO(agendamento.getVeterinario()) : null
        );
    }
}