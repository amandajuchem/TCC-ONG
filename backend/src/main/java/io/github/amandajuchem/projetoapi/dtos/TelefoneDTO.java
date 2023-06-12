package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Telefone;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The TelefoneDTO class represents a Data Transfer Object (DTO) for Telefone entities.
 * It provides a simplified view of a Telefone object for use in API responses.
 * This class implements the Serializable interface.
 */
public record TelefoneDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String numero
) implements Serializable {

    /**
     * Creates a new TelefoneDTO instance based on the provided Telefone object.
     *
     * @param telefone The Telefone object to convert to TelefoneDTO.
     * @return The TelefoneDTO representing the provided Telefone object.
     */
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