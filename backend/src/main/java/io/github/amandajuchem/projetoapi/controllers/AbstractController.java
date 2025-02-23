package io.github.amandajuchem.projetoapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * AbstractController interface for defining common CRUD operations.
 *
 * @param <T>   the entity type
 * @param <DTO> the DTO (Data Transfer Object) type
 */
public interface AbstractController<T, DTO> {

    /**
     * Deletes an item by ID.
     *
     * @param id the ID of the item to delete
     */
    @Operation(summary = "Delete", description = "Deletes an item by ID")
    void delete(UUID id);

    /**
     * Finds all items.
     *
     * @param page      the page number for pagination
     * @param size      the page size for pagination
     * @param sort      the sorting field
     * @param direction the sorting direction
     * @return A Page of DTOs
     */
    @Operation(summary = "Find all", description = "Finds all items")
    Page<DTO> findAll(Integer page, Integer size, String sort, String direction);

    /**
     * Finds an item by ID.
     *
     * @param id the ID of the item to be found
     * @return The DTO of the item
     */
    @Operation(summary = "Find by ID", description = "Finds an item by ID")
    DTO findById(UUID id);

    /**
     * Saves an item.
     *
     * @param object the item to be saved
     * @return The DTO of the saved item
     */
    @Operation(summary = "Save", description = "Saves an item")
    DTO save(T object);

    /**
     * Search items by value.
     *
     * @param value     the value to search for
     * @param page      the page number for pagination
     * @param size      the page size for pagination
     * @param sort      the sorting field
     * @param direction the sorting direction
     * @return A Page of DTOs matching with the value
     */
    @Operation(summary = "Search", description = "Search items")
    Page<DTO> search(String value, Integer page, Integer size, String sort, String direction);

    /**
     * Updates an item.
     *
     * @param id     the ID of the item to be updated
     * @param object the updated item
     * @return The DTO of the updated item
     */
    @Operation(summary = "Update", description = "Updates an item")
    DTO update(UUID id, T object);
}