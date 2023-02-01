package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.enums.Local;
import io.github.amandajuchem.projetoapi.enums.LocalAdocao;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Adocao dto.
 */
public record AdocaoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotNull LocalDateTime dataHora,
        @NotNull Local local,
        @NotNull LocalAdocao localAdocao,
        @NotNull Boolean valeCastracao,
        @NotNull AnimalDTO animal,
        @NotNull TutorDTO tutor,
        @NotNull Set<ImagemDTO> termoResponsabilidade
) implements Serializable {

    /**
     * To dto adocao dto.
     *
     * @param adocao the adocao
     * @return the adocao dto
     */
    public static AdocaoDTO toDTO(Adocao adocao) {

        return new AdocaoDTO(
                adocao.getId(),
                adocao.getCreatedDate(),
                adocao.getLastModifiedDate(),
                adocao.getCreatedByUser(),
                adocao.getModifiedByUser(),
                adocao.getDataHora(),
                adocao.getLocal(),
                adocao.getLocalAdocao(),
                adocao.getValeCastracao(),
                adocao.getAnimal() != null ? AnimalDTO.toDTO(adocao.getAnimal()) : null,
                adocao.getTutor() != null ? TutorDTO.toDTO(adocao.getTutor()) : null,
                adocao.getTermoResponsabilidade() != null ? adocao.getTermoResponsabilidade().stream().map(ImagemDTO::toDTO).collect(Collectors.toSet()) : null
        );
    }
}