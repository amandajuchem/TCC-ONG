package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Usuario dto.
 */
public record UsuarioDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotEmpty String nome,
        @NotEmpty String cpf,
        @NotEmpty String senha,
        @NotNull Boolean status,
        @NotNull Setor setor,
        ImagemDTO foto
) implements Serializable {

    /**
     * To dto usuario dto.
     *
     * @param usuario the usuario
     * @return the usuario dto
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