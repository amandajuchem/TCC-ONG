package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The FeiraAdocaoDTO class represents a Data Transfer Object (DTO) for FeiraAdocao entities.
 * It provides a simplified view of a FeiraAdocao object for use in API responses.
 * This class implements the Serializable interface.
 */
public record FeiraAdocaoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome,
        LocalDateTime dataHora,
        Set<AnimalDTO> animais,
        Set<UsuarioDTO> usuarios
) implements Serializable {

    /**
     * Creates a new FeiraAdocaoDTO instance based on the provided FeiraAdocao object.
     *
     * @param feiraAdocao The FeiraAdocao object to convert to FeiraAdocaoDTO.
     * @return The FeiraAdocaoDTO representing the provided FeiraAdocao object.
     */
    public static FeiraAdocaoDTO toDTO(FeiraAdocao feiraAdocao) {

        return new FeiraAdocaoDTO(
                feiraAdocao.getId(),
                feiraAdocao.getCreatedDate(),
                feiraAdocao.getLastModifiedDate(),
                feiraAdocao.getCreatedByUser(),
                feiraAdocao.getModifiedByUser(),
                feiraAdocao.getNome(),
                feiraAdocao.getDataHora(),
                feiraAdocao.getAnimais() != null ? feiraAdocao.getAnimais().stream().map(AnimalDTO::toDTO).collect(Collectors.toSet()) : null,
                feiraAdocao.getUsuarios() != null ? feiraAdocao.getUsuarios().stream().map(UsuarioDTO::toDTO).collect(Collectors.toSet()) : null
        );
    }
}