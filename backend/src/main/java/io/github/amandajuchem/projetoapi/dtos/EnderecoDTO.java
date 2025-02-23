package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Endereco;
import io.github.amandajuchem.projetoapi.enums.Estado;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record EnderecoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String rua,
        String numeroResidencia,
        String bairro,
        String cidade,
        Estado estado,
        String complemento,
        String cep
) implements Serializable {

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