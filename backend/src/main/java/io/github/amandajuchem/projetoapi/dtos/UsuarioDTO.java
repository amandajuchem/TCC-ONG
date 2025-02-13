package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
        Setor setor
) implements Serializable {

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
                usuario.getSetor()
        );
    }
}