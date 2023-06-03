package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AgendamentoDTO;
import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AgendamentoService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Agendamento controller.
 */
@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    /**
     * Delete agendamento.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all agendamento.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                     @RequestParam(required = false, defaultValue = "desc") String direction) {

        var agendamentos = service.findAll(page, size, sort, direction).map(AgendamentoDTO::toDTO);
        return ResponseEntity.status(OK).body(agendamentos);
    }

    /**
     * Find agendamento by id
     * 
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var agendamento = service.findById(id);
        return ResponseEntity.status(OK).body(AgendamentoDTO.toDTO(agendamento));
    }

    /**
     * Save agendamento.
     *
     * @param agendamento the agendamento
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Agendamento agendamento) {
        agendamento = service.save(agendamento);
        return ResponseEntity.status(CREATED).body(AgendamentoDTO.toDTO(agendamento));
    }

    /**
     * Search agendamento.
     *
     * @param value     the data, nome do animal ou nome do veterinário
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                    @RequestParam(required = false, defaultValue = "desc") String direction) {

        if (value != null) {
            var agendamentos = service.search(value, page, size, sort, direction).map(AgendamentoDTO::toDTO);
            return ResponseEntity.status(OK).body(agendamentos);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update agendamento.
     *
     * @param id          the id
     * @param agendamento the agendamento
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid Agendamento agendamento) {

        if (agendamento.getId().equals(id)) {
            agendamento = service.save(agendamento);
            return ResponseEntity.status(OK).body(AgendamentoDTO.toDTO(agendamento));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}