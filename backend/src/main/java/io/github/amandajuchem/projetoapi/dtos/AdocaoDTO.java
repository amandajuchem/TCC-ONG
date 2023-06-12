package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.enums.Local;
import io.github.amandajuchem.projetoapi.enums.LocalAdocao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The AdocaoDTO class represents a Data Transfer Object (DTO) for Adocao entities.
 * It provides a simplified view of an Adocao object for use in API responses.
 * This class implements the Serializable interface.
 */
public record AdocaoDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        LocalDateTime dataHora,
        Local local,
        LocalAdocao localAdocao,
        Boolean valeCastracao,
        TutorDTO tutor,
        Set<ImagemDTO> termoResponsabilidade
) implements Serializable {

    /**
     * Creates a new AdocaoDTO instance based on the provided adoption object.
     *
     * @param adocao The adoption object to convert to AdocaoDTO.
     * @return The AdocaoDTO representing the provided adoption object.
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
                adocao.getTutor() != null ? TutorDTO.toDTO(adocao.getTutor()) : null,
                adocao.getTermoResponsabilidade() != null ? adocao.getTermoResponsabilidade().stream().map(ImagemDTO::toDTO).collect(Collectors.toSet()) : null
        );
    }
}