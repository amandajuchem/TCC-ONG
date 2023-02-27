package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AgendamentoDTO;
import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Agendamento controller.
 */
@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final FacadeService facade;

    /**
     * Delete agendamento.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        facade.agendamentoDelete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all agendamentos.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                     @RequestParam(required = false, defaultValue = "desc") String direction) {

        var agendamentos = facade.agendamentoFindAll(page, size, sort, direction).map(AgendamentoDTO::toDTO);
        return ResponseEntity.status(OK).body(agendamentos);
    }

    /**
     * Save agendamento.
     *
     * @param agendamento the agendamento
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Agendamento agendamento) {
        agendamento = facade.agendamentoSave(agendamento);
        return ResponseEntity.status(CREATED).body(AgendamentoDTO.toDTO(agendamento));
    }

    /**
     * Search agendamentos.
     *
     * @param value     Data, nome do animal ou nome do veterinário
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of agendamentos
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                    @RequestParam(required = false, defaultValue = "desc") String direction) {

        if (value != null) {
            var agendamentos = facade.agendamentoSearch(value, page, size, sort, direction).map(AgendamentoDTO::toDTO);
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
            agendamento = facade.agendamentoSave(agendamento);
            return ResponseEntity.status(OK).body(AgendamentoDTO.toDTO(agendamento));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}