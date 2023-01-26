package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.enums.Porte;
import io.github.amandajuchem.projetoapi.enums.Sexo;
import io.github.amandajuchem.projetoapi.enums.Situacao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


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
        @NotEmpty String especie,
        String local,
        String localAdocao,
        @NotNull Sexo sexo,
        @NotEmpty String raca,
        LocalDate dataAdocao,
        LocalDate dataResgate,
        @NotEmpty String cor,
        @NotNull Porte porte,
        @NotNull Boolean castrado,
        @NotNull Situacao situacao,
        TutorDTO tutor,
        ImagemDTO foto,
        FichaMedicaDTO fichaMedica
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
                animal.getLocal(),
                animal.getLocalAdocao(),
                animal.getSexo(),
                animal.getRaca(),
                animal.getDataAdocao(),
                animal.getDataResgate(),
                animal.getCor(),
                animal.getPorte(),
                animal.getCastrado(),
                animal.getSituacao(),
                animal.getTutor() != null ? TutorDTO.toDTO(animal.getTutor()) : null,
                animal.getFoto() != null ? ImagemDTO.toDTO(animal.getFoto()) : null,
                animal.getFichaMedica() != null ? FichaMedicaDTO.toDTO(animal.getFichaMedica()) : null
        );
    }
}