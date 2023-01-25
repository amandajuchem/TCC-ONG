package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Atendimento;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Atendimento dto.
 */
public record AtendimentoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotNull LocalDateTime dataHora,
        LocalDateTime dataHoraRetorno,
        @NotEmpty String motivo,
        @NotEmpty String comorbidades,
        @NotEmpty String diagnostico,
        @NotEmpty String exames,
        @NotEmpty String procedimentos,
        @NotEmpty String posologia,
        Set<ImagemDTO> documentos,
        @NotNull AnimalDTO animal,
        @NotNull UsuarioDTO veterinario
) implements Serializable {

    /**
     * To dto atendimento dto.
     *
     * @param atendimento the atendimento
     * @return the atendimento dto
     */
    public static AtendimentoDTO toDTO(Atendimento atendimento) {

        return new AtendimentoDTO(
                atendimento.getId(),
                atendimento.getCreatedDate(),
                atendimento.getLastModifiedDate(),
                atendimento.getCreatedByUser(),
                atendimento.getModifiedByUser(),
                atendimento.getDataHora(),
                atendimento.getDataHoraRetorno(),
                atendimento.getMotivo(),
                atendimento.getComorbidades(),
                atendimento.getDiagnostico(),
                atendimento.getExames(),
                atendimento.getProcedimentos(),
                atendimento.getPosologia(),
                atendimento.getDocumentos() != null ? atendimento.getDocumentos().stream().map(d -> ImagemDTO.toDTO(d)).collect(Collectors.toSet()) : null,
                atendimento.getAnimal() != null ? AnimalDTO.toDTO(atendimento.getAnimal()) : null,
                atendimento.getVeterinario() != null ? UsuarioDTO.toDTO(atendimento.getVeterinario()) : null
        );
    }
}