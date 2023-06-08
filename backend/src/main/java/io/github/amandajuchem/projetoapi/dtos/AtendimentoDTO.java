package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.enums.Motivo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record AtendimentoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        LocalDateTime dataHora,
        Motivo motivo,
        String diagnostico,
        String posologia,
        Set<ImagemDTO> documentos,
        AnimalDTO animal,
        UsuarioDTO veterinario,
        Set<ExameDTO> exames
) implements Serializable {

    public static AtendimentoDTO toDTO(Atendimento atendimento) {

        return new AtendimentoDTO(
            atendimento.getId(),
                atendimento.getCreatedDate(),
                atendimento.getLastModifiedDate(),
                atendimento.getCreatedByUser(),
                atendimento.getModifiedByUser(),
                atendimento.getDataHora(),
                atendimento.getMotivo(),
                atendimento.getDiagnostico(),
                atendimento.getPosologia(),
                atendimento.getDocumentos() != null ? atendimento.getDocumentos().stream().map(d -> ImagemDTO.toDTO(d)).collect(Collectors.toSet()) : null,
                atendimento.getAnimal() != null ? AnimalDTO.toDTO(atendimento.getAnimal()) : null,
                atendimento.getVeterinario() != null ? UsuarioDTO.toDTO(atendimento.getVeterinario()) : null,
                atendimento.getExames() != null ? atendimento.getExames().stream().map(e -> ExameDTO.toDTO(e)).collect(Collectors.toSet()) : null
        );
    }
}