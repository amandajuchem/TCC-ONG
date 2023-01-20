package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.enums.Situacao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Tutor dto.
 */
public record TutorDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotEmpty String nome,
        @NotEmpty String cpf,
        String rg,
        @NotEmpty String telefone,
        @NotNull Situacao situacao,
        String observacao,
        ImagemDTO foto,
        EnderecoDTO endereco
) implements Serializable {

    /**
     * To dto tutor dto.
     *
     * @param tutor the tutor
     * @return the tutor dto
     */
    public static TutorDTO toDTO(Tutor tutor) {

        return new TutorDTO(
                tutor.getId(),
                tutor.getCreatedDate(),
                tutor.getLastModifiedDate(),
                tutor.getCreatedByUser(),
                tutor.getModifiedByUser(),
                tutor.getNome(),
                tutor.getCpf(),
                tutor.getRg(),
                tutor.getTelefone(),
                tutor.getSituacao(),
                tutor.getObservacao(),
                tutor.getFoto() != null ? ImagemDTO.toDTO(tutor.getFoto()) : null,
                EnderecoDTO.toDTO(tutor.getEndereco())
        );
    }
}