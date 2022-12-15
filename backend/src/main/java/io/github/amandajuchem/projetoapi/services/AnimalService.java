package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AnimalRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Animal service.
 */
@Service
public class AnimalService {

    private final AnimalRepository repository;

    /**
     * Instantiates a new Animal service.
     *
     * @param repository the repository
     */
    @Autowired
    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
            }
        }

        throw new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Animal> findAll() {
        return repository.findAll();
    }

    /**
     * Save animal.
     *
     * @param animal the animal
     * @return the animal
     */
    public Animal save(Animal animal) {

        if (animal == null) {
            throw new ValidationException(MessageUtils.ANIMAL_NULL);
        }

        if (validateAnimal(animal)) {
            animal = repository.save(animal);
        }

        return animal;
    }

    /**
     * Validate animal.
     *
     * @param animal the animal
     * @return the boolean
     */
    private boolean validateAnimal(Animal animal) {



        return true;
    }
}