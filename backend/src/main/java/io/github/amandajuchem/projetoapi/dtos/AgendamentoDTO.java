package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Agendamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The AgendamentoDTO class represents a Data Transfer Object (DTO) for Agendamento entities.
 * It provides a simplified view of an Agendamento object for use in API responses.
 * This class implements the Serializable interface.
 */
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

    /**
     * Creates a new AgendamentoDTO instance based on the provided Agendamento object.
     *
     * @param agendamento The Agendamento object to convert to AgendamentoDTO.
     * @return The AgendamentoDTO representing the provided Agendamento object.
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