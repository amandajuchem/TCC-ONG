package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AgendamentoDTO;
import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
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
                                     @RequestParam(required = false, defaultValue = "asc") String direction) {

        var agendamentos = facade.agendamentoFindAll(page, size, sort, direction)
                .map(AgendamentoDTO::toDTO);

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

        var agendamentoSaved = facade.agendamentoSave(agendamento);
        var agendamentoDTO = AgendamentoDTO.toDTO(agendamentoSaved);

        return ResponseEntity.status(CREATED).body(agendamentoDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        if (value != null) {

            var agendamentos = facade.agendamentoFindByDataHoraOrAnimalOrVeterinario(value, page, size, sort, direction)
                    .map(AgendamentoDTO::toDTO);

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

            var agendamentoSaved = facade.agendamentoSave(agendamento);
            var agendamentoDTO = AgendamentoDTO.toDTO(agendamentoSaved);

            return ResponseEntity.status(OK).body(agendamentoDTO);
        }

        throw new ObjectNotFoundException(MessageUtils.AGENDAMENTO_NOT_FOUND);
    }
}