package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.AnimalDTO;
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
 * Service class that implements the AbstractService interface for managing animal objects.
 */
@Service
@RequiredArgsConstructor
public class AnimalService implements AbstractService<Animal, AnimalDTO> {

    private final AnimalRepository repository;

    /**
     * Deletes an animal by ID.
     *
     * @param id the ID of the animal object to be deleted.
     * @throws ObjectNotFoundException if the animal object with the given ID is not found.
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
     * Retrieves all animals.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested AnimalDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<AnimalDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AnimalDTO::toDTO);
    }

    /**
     * Retrieves an animal by ID.
     *
     * @param id the ID of the animal object to be retrieved.
     * @return the AnimalDTO representing the requested animal object.
     * @throws ObjectNotFoundException if the animal object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AnimalDTO findById(UUID id) {
        final var animal = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND));
        return AnimalDTO.toDTO(animal);
    }

    /**
     * Saves an animal.
     *
     * @param animal the animal object to be saved.
     * @return the AnimalDTO representing the saved animal object.
     * @throws ValidationException if the animal object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AnimalDTO save(Animal animal) {

        if (animal == null) {
            throw new ValidationException(MessageUtils.ANIMAL_NULL);
        }

        if (validate(animal)) {
            animal = repository.save(animal);
        }

        return AnimalDTO.toDTO(animal);
    }

    /**
     * Search for animals by value.
     *
     * @param value     the value to search for (animal's name) case-insensitive.
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested AnimalDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<AnimalDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AnimalDTO::toDTO);
    }

    /**
     * Validates an animal.
     *
     * @param animal the animal object to be validated.
     * @return true if the animal object is valid.
     * @throws ValidationException if the animal object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Animal animal) {
        return true;
    }
}