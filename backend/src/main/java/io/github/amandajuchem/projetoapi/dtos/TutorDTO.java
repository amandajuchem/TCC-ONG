package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Set<TelefoneDTO> telefones,
        EnderecoDTO endereco,
        Set<AdocaoDTO> adocoes,
        Set<ObservacaoDTO> observacoes
) implements Serializable {

    public static Tutor toTutor(TutorDTO tutorDTO) {
        final var tutor = new Tutor();
        BeanUtils.copyProperties(tutorDTO, tutor);
        return tutor;
    }

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
                tutor.getTelefones() != null ? tutor.getTelefones().stream().map(TelefoneDTO::toDTO).collect(Collectors.toSet()) : null,
                tutor.getEndereco() != null ? EnderecoDTO.toDTO(tutor.getEndereco()) : null,
                null,
                null
        );
    }
}