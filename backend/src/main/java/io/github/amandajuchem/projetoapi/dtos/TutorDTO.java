package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.enums.Situacao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        @NotNull Situacao situacao,
        ImagemDTO foto,
        Set<TelefoneDTO> telefones,
        EnderecoDTO endereco,
        Set<ObservacaoDTO> observacoes
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
                tutor.getSituacao(),
                tutor.getFoto() != null ? ImagemDTO.toDTO(tutor.getFoto()) : null,
                tutor.getTelefones() != null ? tutor.getTelefones().stream().map(t -> TelefoneDTO.toDTO(t)).collect(Collectors.toSet()) : null,
                tutor.getEndereco() != null ? EnderecoDTO.toDTO(tutor.getEndereco()) : null,
                tutor.getObservacoes() != null ? tutor.getObservacoes().stream().map(o -> ObservacaoDTO.toDTO(o)).collect(Collectors.toSet()) : null
        );
    }
}