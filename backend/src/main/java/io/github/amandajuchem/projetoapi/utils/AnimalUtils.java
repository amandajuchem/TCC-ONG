package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AnimalService;
import io.github.amandajuchem.projetoapi.services.FichaMedicaService;
import io.github.amandajuchem.projetoapi.services.ImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * The type Animal utils.
 */
@Component
@RequiredArgsConstructor
public class AnimalUtils {

    private final AnimalService animalService;
    private final FichaMedicaService fichaMedicaService;
    private final ImagemService imagemService;

    /**
     * Delete animal.
     *
     * @param id the id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        var animal = animalService.findById(id);

        if (animal.getFoto() != null) {
            imagemService.delete(animal.getFoto().getId());
        }

        if (animal.getFichaMedica() != null) {
            fichaMedicaService.delete(animal.getFichaMedica().getId());
        }

        animalService.delete(animal.getId());
    }

    /**
     * Save animal.
     *
     * @param animal     the animal
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the animal
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Animal save(Animal animal, MultipartFile novaFoto, UUID antigaFoto) {

        try {

            if (animal.getId() == null) {

                var fichaMedica = animal.getFichaMedica();

                animal.setFichaMedica(null);
                animal = animalService.save(animal);
                fichaMedica.setAnimal(animal);
                fichaMedica = fichaMedicaService.save(fichaMedica);
                animal.setFichaMedica(fichaMedica);
            }

            else {

                var fichaMedica = animal.getFichaMedica();

                fichaMedica = fichaMedicaService.save(fichaMedica);
                animal = animalService.save(animal);
                animal.setFichaMedica(fichaMedica);
            }

            if (novaFoto != null) {

                var file = FileUtils.save(novaFoto, FileUtils.IMAGES_DIRECTORY);

                var imagem = Imagem.builder()
                        .nome(file.getName())
                        .animal(animal)
                        .build();

                imagem = imagemService.save(imagem);
                animal.setFoto(imagem);
            }

            if (antigaFoto != null) {
                imagemService.delete(antigaFoto);
            }

            return animal;
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (IOException ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}