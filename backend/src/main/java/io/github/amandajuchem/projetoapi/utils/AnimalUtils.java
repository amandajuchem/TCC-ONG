package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * The type Animal utils.
 */
@Component
public class AnimalUtils {

    private final FacadeService facade;

    /**
     * Instantiates a new Animal utils.
     *
     * @param facade the facade
     */
    @Autowired
    public AnimalUtils(FacadeService facade) {
        this.facade = facade;
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
                        .animal(animal)
                        .build();

                imagem = facade.imagemSave(imagem);
                animal.setFoto(imagem);
            }

            if (antigaFoto != null) {
                facade.imagemDelete(antigaFoto);
            }

            return facade.animalSave(animal);
        } catch (Exception ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}