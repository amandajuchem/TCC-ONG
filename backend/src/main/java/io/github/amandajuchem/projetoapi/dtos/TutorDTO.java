package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.enums.Situacao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The TutorDTO class represents a Data Transfer Object (DTO) for Tutor entities.
 * It provides a simplified view of a Tutor object for use in API responses.
 * This class implements the Serializable interface.
 */
public record TutorDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome,
        String cpf,
        String rg,
        Situacao situacao,
        ImagemDTO foto,
        Set<TelefoneDTO> telefones,
        EnderecoDTO endereco,
        Set<AdocaoDTO> adocoes,
        Set<ObservacaoDTO> observacoes
) implements Serializable {

    /**
     * Creates a new TutorDTO instance based on the provided Tutor object.
     *
     * @param tutor The Tutor object to convert to TutorDTO.
     * @return The TutorDTO representing the provided Tutor object.
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
                tutor.getTelefones() != null ? tutor.getTelefones().stream().map(TelefoneDTO::toDTO).collect(Collectors.toSet()) : null,
                tutor.getEndereco() != null ? EnderecoDTO.toDTO(tutor.getEndereco()) : null,
                null,
                null
        );
    }
}