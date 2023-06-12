package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AgendamentoDTO;
import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AgendamentoService;
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
 * Controller class for managing schedules.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints for schedules management")
public class AgendamentoController implements AbstractController<Agendamento, AgendamentoDTO> {

    private final AgendamentoService service;

    /**
     * Delete a scheduling by ID.
     *
     * @param id The ID of the scheduling to be deleted.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieve all schedules.
     *
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "dataHora").
     * @param direction The sorting direction (optional, default: "desc").
     * @return A ResponseEntity containing a page of AgendamentoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<AgendamentoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                        @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var agendamentos = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(agendamentos);
    }

    /**
     * Retrieve a scheduling by ID.
     *
     * @param id The ID of the scheduling to be retrieved.
     * @return A ResponseEntity containing the AgendamentoDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> findById(@PathVariable UUID id) {
        final var agendamento = service.findById(id);
        return ResponseEntity.status(OK).body(agendamento);
    }

    /**
     * Save a scheduling.
     *
     * @param agendamento The scheduling object to be saved.
     * @return A ResponseEntity containing the saved AgendamentoDTO object, with the HTTP status of 201 (CREATED).
     */
    @Override
    @PostMapping
    public ResponseEntity<AgendamentoDTO> save(@RequestBody @Valid Agendamento agendamento) {
        final var agendamentoSaved = service.save(agendamento);
        return ResponseEntity.status(CREATED).body(agendamentoSaved);
    }

    /**
     * Search for schedules by value.
     *
     * @param value     The search value.
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "dataHora").
     * @param direction The sorting direction (optional, default: "desc").
     * @return A ResponseEntity containing a page of AgendamentoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<AgendamentoDTO>> search(@RequestParam String value,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                       @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var agendamentos = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(agendamentos);
    }

    /**
     * Update a scheduling.
     *
     * @param id          The ID of the scheduling to be updated.
     * @param agendamento The updated scheduling object.
     * @return A ResponseEntity containing the updated AgendamentoDTO object, with HTTP status of 200 (OK).
     * @throws ValidationException If the provided scheduling ID does not match the path ID.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> update(@PathVariable UUID id, @RequestBody @Valid Agendamento agendamento) {

        if (agendamento.getId().equals(id)) {
            final var agendamentoSaved = service.save(agendamento);
            return ResponseEntity.status(OK).body(agendamentoSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}