package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Adocao;

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
        @NotNull Boolean valeCastracao,
        @NotNull AnimalDTO animal,
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
                adocao.getValeCastracao(),
                adocao.getAnimal() != null ? AnimalDTO.toDTO(adocao.getAnimal()) : null,
                adocao.getTermoResponsabilidade() != null ? adocao.getTermoResponsabilidade().stream().map(t -> ImagemDTO.toDTO(t)).collect(Collectors.toSet()) : null
        );
    }
}