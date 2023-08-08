package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Telefone;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record TelefoneDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String numero
) implements Serializable {

    public static TelefoneDTO toDTO(Telefone telefone) {

        return new TelefoneDTO(
                telefone.getId(),
                telefone.getCreatedDate(),
                telefone.getLastModifiedDate(),
                telefone.getCreatedByUser(),
                telefone.getModifiedByUser(),
                telefone.getNumero()
        );
    }
}