package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Endereco;
import io.github.amandajuchem.projetoapi.enums.Estado;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Endereco dto.
 */
public record EnderecoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotEmpty String rua,
        @NotNull String numeroResidencia,
        @NotEmpty String bairro,
        @NotEmpty String cidade,
        @NotNull Estado estado,
        String complemento,
        @NotEmpty String cep
) implements Serializable {

    /**
     * To dto endereco dto.
     *
     * @param endereco the endereco
     * @return the endereco dto
     */
    public static EnderecoDTO toDTO(Endereco endereco) {

        return new EnderecoDTO(
                endereco.getId(),
                endereco.getCreatedDate(),
                endereco.getLastModifiedDate(),
                endereco.getCreatedByUser(),
                endereco.getModifiedByUser(),
                endereco.getRua(),
                endereco.getNumeroResidencia(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getComplemento(),
                endereco.getCep()
        );
    }
}