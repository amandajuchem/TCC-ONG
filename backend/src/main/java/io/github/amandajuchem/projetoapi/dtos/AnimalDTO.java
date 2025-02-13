package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.enums.Especie;
import io.github.amandajuchem.projetoapi.enums.Porte;
import io.github.amandajuchem.projetoapi.enums.Sexo;
import io.github.amandajuchem.projetoapi.enums.Situacao;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record AnimalDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome,
        Integer idade,
        Especie especie,
        Sexo sexo,
        String raca,
        String cor,
        Porte porte,
        Situacao situacao,
        ArquivoDTO foto,
        FichaMedicaDTO fichaMedica,
        Set<AdocaoDTO> adocoes
) implements Serializable {

    public static Animal toAnimal(AnimalDTO animalDTO) {
        final var animal = new Animal();
        BeanUtils.copyProperties(animalDTO, animal);
        return animal;
    }

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
                animal.getFoto() != null ? ArquivoDTO.toDTO(animal.getFoto()) : null,
                animal.getFichaMedica() != null ? FichaMedicaDTO.toDTO(animal.getFichaMedica()) : null,
                null
        );
    }
}