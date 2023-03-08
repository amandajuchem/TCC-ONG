package io.github.amandajuchem.projetoapi.services;

import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * The interface Abstract service.
 *
 * @param <T> the type parameter
 */
public interface AbstractService<T> {

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(UUID id);

    /**
     * Find all.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the page
     */
    Page<T> findAll(Integer page, Integer size, String sort, String direction);

    /**
     * Find by id.
     *
     * @param id the id
     * @return the t
     */
    T findById(UUID id);

    /**
     * Save.
     *
     * @param object the object
     * @return the t
     */
    T save(T object);

    /**
     * Validate.
     *
     * @param object the object
     * @return the boolean
     */
    boolean validate(T object);
}