package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * The type Animal utils.
 */
@Component
@RequiredArgsConstructor
public class AnimalUtils {

    private final FacadeService facade;

    /**
     * Delete animal.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        var animal = facade.animalFindById(id);

        if (animal.getFoto() != null) {
            facade.imagemDelete(animal.getFoto().getId());
        }

        if (animal.getFichaMedica() != null) {
            facade.fichaMedicaDelete(animal.getFichaMedica().getId());
        }

        facade.animalDelete(animal.getId());
    }

    /**
     * Save animal.
     *
     * @param animal     the animal
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the animal
     */
    public Animal save(Animal animal, MultipartFile novaFoto, UUID antigaFoto) {

        try {

            if (animal.getId() == null) {

                var fichaMedica = animal.getFichaMedica();

                animal.setFichaMedica(null);
                animal = facade.animalSave(animal);
                fichaMedica.setAnimal(animal);
                fichaMedica = facade.fichaMedicaSave(fichaMedica);
                animal.setFichaMedica(fichaMedica);
            }

            else {

                var fichaMedica = animal.getFichaMedica();

                fichaMedica = facade.fichaMedicaSave(fichaMedica);
                animal = facade.animalSave(animal);
                animal.setFichaMedica(fichaMedica);
            }

            if (novaFoto != null) {

                var file = FileUtils.save(novaFoto, FileUtils.IMAGES_DIRECTORY);

                var imagem = Imagem.builder()
                        .nome(file.getName())
                        .animal(animal)
                        .build();

                imagem = facade.imagemSave(imagem);
                animal.setFoto(imagem);
            }

            if (antigaFoto != null) {
                facade.imagemDelete(antigaFoto);
            }

            return animal;
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (IOException ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}