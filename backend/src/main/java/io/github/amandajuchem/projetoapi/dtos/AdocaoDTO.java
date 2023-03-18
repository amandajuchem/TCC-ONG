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
 * The type Adocao dto.
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
                adocao.getTutor() != null ? TutorDTO.toDTO(adocao.getTutor()) : null,
                adocao.getTermoResponsabilidade() != null ? adocao.getTermoResponsabilidade().stream().map(ImagemDTO::toDTO).collect(Collectors.toSet()) : null
        );
    }
}