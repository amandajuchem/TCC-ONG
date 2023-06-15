package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.ExameDTO;
import io.github.amandajuchem.projetoapi.entities.Exame;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.ExameService;
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
 * Controller class for managing exams.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
@Tag(name = "Exames", description = "Endpoints for exams management")
public class ExameController implements AbstractController<Exame, ExameDTO> {

    private final ExameService service;

    /**
     * Deletes an exam by ID.
     *
     * @param id The ID of the exam to be deleted.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieves all exams.
     *
     * @param page       The page number for pagination (optional, default: 0).
     * @param size       The page size for pagination (optional, default: 10).
     * @param sort       The sorting field (optional, default: "nome").
     * @param direction  The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of ExameDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<ExameDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                                  @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var exames = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(exames);
    }

    /**
     * Retrieves an exam by ID.
     *
     * @param id The ID of the exam to be retrieved.
     * @return A ResponseEntity containing the ExameDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ExameDTO> findById(@PathVariable UUID id) {
        final var exame = service.findById(id);
        return ResponseEntity.status(OK).body(exame);
    }

    /**
     * Saves an exam.
     *
     * @param exame The exam object to be saved.
     * @return A ResponseEntity containing the saved ExameDTO object, with the HTTP status of 201 (CREATED).
     */
    @Override
    @PostMapping
    public ResponseEntity<ExameDTO> save(@RequestBody @Valid Exame exame) {
        final var exameSaved = service.save(exame);
        return ResponseEntity.status(CREATED).body(exameSaved);
    }

    /**
     * Search for exams by value.
     *
     * @param value      The value to search for.
     * @param page       The page number for pagination (optional, default: 0).
     * @param size       The page size for pagination (optional, default: 10).
     * @param sort       The sorting field (optional, default: "nome").
     * @param direction  The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of ExameDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<ExameDTO>> search(@RequestParam String value,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                 @RequestParam(required = false, defaultValue = "nome") String sort,
                                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var exames = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(exames);
    }

    /**
     * Updates an exam.
     *
     * @param id    The ID of the exam to be updated.
     * @param exame The updated exam object.
     * @return A ResponseEntity containing the updated ExameDTO object, with HTTP status of 200 (OK).
     * @throws ValidationException If the provided exam ID does not match the path ID.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ExameDTO> update(@PathVariable UUID id, @RequestBody @Valid Exame exame) {

        if (exame.getId().equals(id)) {
            final var exameSaved = service.save(exame);
            return ResponseEntity.status(OK).body(exameSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}