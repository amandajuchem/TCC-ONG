package io.github.amandajuchem.projetoapi.services;

import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * This interface represents an abstract service for managing objects of type T.
 * It provides common CRUD (Create, Read, Update, Delete) operations.
 *
 * @param <T>   the type of the objects managed by the service.
 * @param <DTO> the DTO (Data Transfer Object) type used for data exchange.
 */
public interface AbstractService<T, DTO> {

    /**
     * Deletes an object by ID.
     *
     * @param id the ID of the object to be deleted.
     */
    void delete(UUID id);

    /**
     * Retrieves all objects.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested objects.
     */
    Page<DTO> findAll(Integer page, Integer size, String sort, String direction);

    /**
     * Retrieves an object by ID.
     *
     * @param id the ID of the object to be retrieved.
     * @return the DTO representing the requested object.
     */
    DTO findById(UUID id);

    /**
     * Saves an object.
     *
     * @param object the object to be saved.
     * @return the DTO representing the saved object.
     */
    DTO save(T object);

    /**
     * Search for objects be value.
     *
     * @param value     the value to search for.
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested objects.
     */
    Page<DTO> search(String value, Integer page, Integer size, String sort, String direction);

    /**
     * Validates an object.
     *
     * @param object the object to be validated.
     * @return true if the object is valid.
     */
    boolean validate(T object);
}