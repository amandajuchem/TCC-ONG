package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AgendamentoDTO;
import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Agendamento controller.
 */
@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
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
    public ResponseEntity<?> findAll() {

        var agendamentosDTO = facade.agendamentoFindAll().stream()
                .map(AgendamentoDTO::toDTO)
                .toList();

        return ResponseEntity.status(OK).body(agendamentosDTO);
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