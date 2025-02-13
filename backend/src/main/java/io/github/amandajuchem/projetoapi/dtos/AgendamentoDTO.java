package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Agendamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record AgendamentoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        LocalDateTime dataHora,
        String descricao,
        AnimalDTO animal,
        UsuarioDTO veterinario
) implements Serializable {

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