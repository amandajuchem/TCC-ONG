package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AnimalRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Animal service.
 */
@Service
@RequiredArgsConstructor
public class AnimalService implements AbstractService<Animal> {

    private final AnimalRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
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
     * Find all animal.
     *
     * @return the animal list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Animal> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find animal by id.
     *
     * @param id the id
     * @return the animal
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Animal findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND);
        });
    }

    /**
     * Save animal.
     *
     * @param animal the animal
     * @return the animal
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Animal save(Animal animal) {

        if (animal == null) {
            throw new ValidationException(MessageUtils.ANIMAL_NULL);
        }

        if (validate(animal)) {
            animal = repository.save(animal);
        }

        return animal;
    }

    /**
     * Search animal.
     *
     * @param value     the nome
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the animal list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Animal> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate animal.
     *
     * @param animal the animal
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Animal animal) {

        return true;
    }
}