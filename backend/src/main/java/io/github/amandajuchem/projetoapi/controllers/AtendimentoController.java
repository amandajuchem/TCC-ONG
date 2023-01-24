package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
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
 * The type Atendimento controller.
 */
@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AtendimentoController {

    private final FacadeService facade;

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Save response entity.
     *
     * @param atendimento the atendimento
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid Atendimento atendimento) {
        return ResponseEntity.status(CREATED).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id          the id
     * @param atendimento the atendimento
     * @return the response entity
     */
    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody @Valid Atendimento atendimento) {

        if (atendimento.getId().equals(id)) {
            return ResponseEntity.status(OK).body(null);
        }

        throw new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND);
    }
}