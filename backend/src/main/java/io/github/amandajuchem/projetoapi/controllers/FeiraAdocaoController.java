package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.FeiraAdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FeiraAdocaoService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Controller class for managing adoption fairs.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/feiras-adocao")
@RequiredArgsConstructor
@Tag(name = "Feiras de Adoção", description = "Endpoints for adoption fairs management")
public class FeiraAdocaoController implements AbstractController<FeiraAdocao, FeiraAdocaoDTO> {

    private final FeiraAdocaoService service;

    /**
     * Deletes an adoption fair by ID.
     *
     * @param id The ID of the adoption fair to be deleted.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieves all adoption fairs.
     *
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of FeiraAdocaoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<FeiraAdocaoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "nome") String sort,
                                                        @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var feirasAdocao = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(feirasAdocao);
    }

    /**
     * Retrieves an adoption fair by ID.
     *
     * @param id The ID of the adoption fair to be retrieved.
     * @return A ResponseEntity containing the FeiraAdocaoDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FeiraAdocaoDTO> findById(@PathVariable UUID id) {
        final var feiraAdocao = service.findById(id);
        return ResponseEntity.status(OK).body(feiraAdocao);
    }

    /**
     * Saves an adoption fair.
     *
     * @param feiraAdocao The adoption fair object to be saved.
     * @return A ResponseEntity containing the saved FeiraAdocaoDTO object, with the HTTP status of 201 (CREATED).
     */
    @Override
    @PostMapping
    public ResponseEntity<FeiraAdocaoDTO> save(@RequestBody @Valid FeiraAdocao feiraAdocao) {
        final var feiraAdocaoSaved = service.save(feiraAdocao);
        return ResponseEntity.status(CREATED).body(feiraAdocaoSaved);
    }

    /**
     * Search for adoption fairs by value.
     *
     * @param value     The value to search for.
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of FeiraAdocaoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<FeiraAdocaoDTO>> search(@RequestParam String value,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "nome") String sort,
                                                       @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var feirasAdocao = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(feirasAdocao);
    }

    /**
     * Updates an adoption fair.
     *
     * @param id           The ID of the adoption fair to be updated.
     * @param feiraAdocao  The updated adoption fair object.
     * @return A ResponseEntity containing the updated FeiraAdocaoDTO object, with HTTP status of 200 (OK).
     * @throws ValidationException If the provided adoption fair ID does not match the path ID.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FeiraAdocaoDTO> update(@PathVariable UUID id, @RequestBody @Valid FeiraAdocao feiraAdocao) {

        if (feiraAdocao.getId().equals(id)) {
            final var feiraAdocaoSaved = service.save(feiraAdocao);
            return ResponseEntity.status(OK).body(feiraAdocaoSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}