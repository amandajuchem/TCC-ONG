package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.ObservacaoDTO;
import io.github.amandajuchem.projetoapi.entities.Observacao;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.ObservacaoService;
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
 * Controller class for managing observations.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/observacoes")
@RequiredArgsConstructor
@Tag(name = "Observações", description = "Endpoints for observations management")
public class ObservacaoController implements AbstractController<Observacao, ObservacaoDTO> {

    private final ObservacaoService service;

    /**
     * Deletes an observation by ID.
     *
     * @param id The ID of the observation to be deleted.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieves all observations.
     *
     * @param page       The page number for pagination (optional, default: 0).
     * @param size       The page size for pagination (optional, default: 10).
     * @param sort       The sorting field (optional, default: "createdDate").
     * @param direction  The sorting direction (optional, default: "desc").
     * @return @return A ResponseEntity containing a page of ObservacaoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<ObservacaoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                                       @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var observacoes = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(observacoes);
    }

    /**
     * Retrieves an observation by ID.
     *
     * @param id The ID of the observation to be retrieved.
     * @return A ResponseEntity containing the ObservacaoDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ObservacaoDTO> findById(@PathVariable UUID id) {
        final var observacao = service.findById(id);
        return ResponseEntity.status(OK).body(observacao);
    }

    /**
     * Saves an observation.
     *
     * @param observacao The observation to be saved.
     * @return A ResponseEntity containing the saved ObservacaoDTO object, with the HTTP status of 201 (CREATED).
     */
    @Override
    @PostMapping
    public ResponseEntity<ObservacaoDTO> save(@RequestBody @Valid Observacao observacao) {
        final var observacaoSaved = service.save(observacao);
        return ResponseEntity.status(CREATED).body(observacaoSaved);
    }

    /**
     * Search for observations by value.
     *
     * @param value      The value to search for.
     * @param page       The page number for pagination (optional, default: 0).
     * @param size       The page size for pagination (optional, default: 10).
     * @param sort       The sorting field (optional, default: "createdDate").
     * @param direction  The sorting direction (optional, default: "desc").
     * @return A ResponseEntity containing a page of ObservacaoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<ObservacaoDTO>> search(@RequestParam String value,
                                                      @RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, defaultValue = "10") Integer size,
                                                      @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                                      @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var observacoes = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(observacoes);
    }

    /**
     * Updates an observation.
     *
     * @param id          The ID of the observation to be updated.
     * @param observacao  The updated observation.
     * @return A ResponseEntity containing the updated ObservacaoDTO object, with HTTP status of 200 (OK).
     * @throws ValidationException If the provided observation ID does not match the path ID.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ObservacaoDTO> update(@PathVariable UUID id,
                                                @RequestBody @Valid Observacao observacao) {

        if (observacao.getId().equals(id)) {
            final var observacaoSaved = service.save(observacao);
            return ResponseEntity.status(OK).body(observacaoSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}