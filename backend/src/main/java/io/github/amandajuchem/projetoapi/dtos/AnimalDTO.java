package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.enums.Especie;
import io.github.amandajuchem.projetoapi.enums.Porte;
import io.github.amandajuchem.projetoapi.enums.Sexo;
import io.github.amandajuchem.projetoapi.enums.Situacao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * The type Animal dto.
 */
public record AnimalDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        @NotEmpty String nome,
        @NotNull Integer idade,
        @NotEmpty Especie especie,
        @NotNull Sexo sexo,
        @NotEmpty String raca,
        @NotEmpty String cor,
        @NotNull Porte porte,
        @NotNull Situacao situacao,
        ImagemDTO foto,
        FichaMedicaDTO fichaMedica,
        Set<AdocaoDTO> adocoes
) implements Serializable {

    /**
     * To dto animal dto.
     *
     * @param animal the animal
     * @return the animal dto
     */
    public static AnimalDTO toDTO(Animal animal) {

        return new AnimalDTO(
                animal.getId(),
                animal.getCreatedDate(),
                animal.getLastModifiedDate(),
                animal.getCreatedByUser(),
                animal.getModifiedByUser(),
                animal.getNome(),
                animal.getIdade(),
                animal.getEspecie(),
                animal.getSexo(),
                animal.getRaca(),
                animal.getCor(),
                animal.getPorte(),
                animal.getSituacao(),
                animal.getFoto() != null ? ImagemDTO.toDTO(animal.getFoto()) : null,
                animal.getFichaMedica() != null ? FichaMedicaDTO.toDTO(animal.getFichaMedica()) : null,
                animal.getAdocoes() != null ? animal.getAdocoes().stream().map(a -> AdocaoDTO.toDTO(a)).collect(Collectors.toSet()) : null
        );
    }
}