package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * The type Animal utils.
 */
@Component
@RequiredArgsConstructor(onConstructor_= {@Autowired})
public class AnimalUtils {

    private final FacadeService facade;

    /**
     * Delete animal.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        var animal = facade.animalFindById(id);

        facade.animalDelete(animal.getId());

        if (animal.getFoto() != null) {
            FileUtils.delete(animal.getFoto().getNome(), FileUtils.IMAGES_DIRECTORY);
        }
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

            animal = facade.animalSave(animal);

            if (novaFoto != null) {

                var file = FileUtils.save(novaFoto, FileUtils.IMAGES_DIRECTORY);

                var imagem = Imagem.builder()
                        .nome(file.getName())
                        .build();

                animal.setFoto(imagem);
            }

            if (antigaFoto != null) {
                facade.imagemDelete(antigaFoto);
            }

            return facade.animalSave(animal);
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (IOException ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}