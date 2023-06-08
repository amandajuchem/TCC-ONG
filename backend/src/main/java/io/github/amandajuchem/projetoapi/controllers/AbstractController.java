package io.github.amandajuchem.projetoapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public interface AbstractController<T, DTO> {

    @Operation(summary = "Delete", description = "Delete an item by ID")
    @ResponseStatus(OK)
    ResponseEntity<Void> delete(UUID id);

    @Operation(summary = "Find all", description = "Find all items")
    @ResponseStatus(OK)
    ResponseEntity<Page<DTO>> findAll(Integer page, Integer size, String sort, String direction);

    @Operation(summary = "Find by ID", description = "Find an item by ID")
    @ResponseStatus(OK)
    ResponseEntity<DTO> findById(UUID id);

    @Operation(summary = "Save", description = "Save an item")
    @ResponseStatus(CREATED)
    ResponseEntity<DTO> save(T object);

    @Operation(summary = "Search", description = "Search items")
    @ResponseStatus(OK)
    ResponseEntity<Page<DTO>> search(String value, Integer page, Integer size, String sort, String direction);

    @Operation(summary = "Update", description = "Update an item")
    @ResponseStatus(OK)
    ResponseEntity<DTO> update(UUID id, T object);
}