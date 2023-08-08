package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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