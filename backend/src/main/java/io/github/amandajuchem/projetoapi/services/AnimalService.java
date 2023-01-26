package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AnimalRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Animal service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AnimalService {

    private final AnimalRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Animal> findAll() {
        return repository.findAll();
    }

    /**
     * Find by id animal.
     *
     * @param id the id
     * @return the animal
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Animal findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND);
        });
    }

    /**
     * Find by nome contains list.
     *
     * @param nome the nome
     * @return the list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Animal> findByNomeContains(String nome) {
        return repository.findByNomeContainsIgnoreCase(nome);
    }

    /**
     * Save animal.
     *
     * @param animal the animal
     * @return the animal
     */
    @Transactional(propagation = Propagation.REQUIRED)
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