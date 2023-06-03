package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.ObservacaoDTO;
import io.github.amandajuchem.projetoapi.entities.Observacao;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.ObservacaoService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Observacao controller.
 */
@RestController
@RequestMapping("/observacoes")
@RequiredArgsConstructor
public class ObservacaoController {

    private final ObservacaoService service;

    /**
     * Delete.
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
     * Find all.
     *
     * @param tutorId   the tutor id
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam UUID tutorId,
                                     @RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                     @RequestParam(required = false, defaultValue = "desc") String direction) {

        var observacoes = service.findAll(tutorId, page, size, sort, direction).map(ObservacaoDTO::toDTO);
        return ResponseEntity.status(OK).body(observacoes);
    }

    /**
     * Save.
     *
     * @param observacao the observacao
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Observacao observacao) {
        observacao = service.save(observacao);
        return ResponseEntity.status(CREATED).body(ObservacaoDTO.toDTO(observacao));
    }

    /**
     * Update.
     *
     * @param id         the id
     * @param observacao the observacao
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestBody @Valid Observacao observacao) {

        if (observacao.getId().equals(id)) {
            observacao = service.save(observacao);
            return ResponseEntity.status(OK).body(ObservacaoDTO.toDTO(observacao));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}