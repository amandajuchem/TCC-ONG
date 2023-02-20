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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Animal service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
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
    public Page<Animal> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find by id animal.
     *
     * @param id the id
     * @return the animal
     */
    public Animal findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND);
        });
    }

    /**
     * Find by nome contains list.
     *
     * @param nome      the nome
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list
     */
    public Page<Animal> findByNomeContains(String nome, Integer page, Integer size, String sort, String direction) {
        return repository.findByNomeContainsIgnoreCase(nome, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
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