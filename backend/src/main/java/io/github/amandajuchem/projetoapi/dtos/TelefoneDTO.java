package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Telefone;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Telefone dto.
 */
public record TelefoneDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotEmpty String numero
) implements Serializable {

    /**
     * To dto telefone dto.
     *
     * @param telefone the telefone
     * @return the telefone dto
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