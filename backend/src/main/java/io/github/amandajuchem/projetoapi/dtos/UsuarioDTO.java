package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The UsuarioDTO class represents a Data Transfer Object (DTO) for Usuario entities.
 * It provides a simplified view of an Usuario object for use in API responses.
 * This class implements the Serializable interface.
 */
public record UsuarioDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome,
        String cpf,
        String senha,
        Boolean status,
        Setor setor,
        ImagemDTO foto
) implements Serializable {

    /**
     * Creates a new UsuarioDTO instance based on the provided Usuario object.
     *
     * @param usuario The Usuario object to convert to UsuarioDTO.
     * @return The UsuarioDTO representing the provided Usuario object.
     */
    public static UsuarioDTO toDTO(Usuario usuario) {

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getCreatedDate(),
                usuario.getLastModifiedDate(),
                usuario.getCreatedByUser(),
                usuario.getModifiedByUser(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getSenha(),
                usuario.getStatus(),
                usuario.getSetor(),
                usuario.getFoto() != null ? ImagemDTO.toDTO(usuario.getFoto()) : null
        );
    }
}