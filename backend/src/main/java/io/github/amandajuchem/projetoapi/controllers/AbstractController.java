package io.github.amandajuchem.projetoapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
     * @return a ResponseEntity with a status code of 200 OK
     */
    @Operation(summary = "Delete", description = "Deletes an item by ID")
    @ResponseStatus(OK)
    ResponseEntity<Void> delete(@PathVariable UUID id);

    /**
     * Finds all items.
     *
     * @param page       the page number for pagination
     * @param size       the page size for pagination
     * @param sort       the sorting field
     * @param direction  the sorting direction
     * @return a ResponseEntity with a Page of DTOs
     */
    @Operation(summary = "Find all", description = "Finds all items")
    @ResponseStatus(OK)
    ResponseEntity<Page<DTO>> findAll(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String sort, @RequestParam String direction);

    /**
     * Finds an item by ID.
     *
     * @param id the ID of the item to be found
     * @return a ResponseEntity with the DTO of the item
     */
    @Operation(summary = "Find by ID", description = "Finds an item by ID")
    @ResponseStatus(OK)
    ResponseEntity<DTO> findById(@PathVariable UUID id);

    /**
     * Saves an item.
     *
     * @param object the item to be saved
     * @return a ResponseEntity with the DTO of the saved item
     */
    @Operation(summary = "Save", description = "Saves an item")
    @ResponseStatus(CREATED)
    ResponseEntity<DTO> save(@RequestBody T object);

    /**
     * Search items by value.
     *
     * @param value      the value to search for
     * @param page       the page number for pagination
     * @param size       the page size for pagination
     * @param sort       the sorting field
     * @param direction  the sorting direction
     * @return a ResponseEntity with a Page of DTOs matching with the value
     */
    @Operation(summary = "Search", description = "Search items")
    @ResponseStatus(OK)
    ResponseEntity<Page<DTO>> search(@RequestParam String value, @RequestParam Integer page, @RequestParam Integer size, @RequestParam String sort, @RequestParam String direction);

    /**
     * Updates an item.
     *
     * @param id     the ID of the item to be updated
     * @param object the updated item
     * @return a ResponseEntity with the DTO of the updated item
     */
    @Operation(summary = "Update", description = "Updates an item")
    @ResponseStatus(OK)
    ResponseEntity<DTO> update(@PathVariable UUID id, @RequestBody T object);
}