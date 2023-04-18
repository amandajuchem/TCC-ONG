package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.FeiraAdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FeiraAdocaoService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Feira Adocao controller.
 */
@RestController
@RequestMapping("/feiras-adocao")
@RequiredArgsConstructor
public class FeiraAdocaoController {

    private final FeiraAdocaoService service;

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
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "nome") String sort,
                                     @RequestParam(required = false, defaultValue = "asc") String direction) {

        var exames = service.findAll(page, size, sort, direction).map(FeiraAdocaoDTO::toDTO);
        return ResponseEntity.status(OK).body(exames);
    }

    /**
     * Find Feira Adoção by id.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var feiraAdocao = service.findById(id);
        return ResponseEntity.status(OK).body(FeiraAdocaoDTO.toDTO(feiraAdocao));
    }

    /**
     * Save.
     *
     * @param feiraAdocao the feira adocao
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid FeiraAdocao feiraAdocao) {
        feiraAdocao = service.save(feiraAdocao);
        return ResponseEntity.status(CREATED).body(FeiraAdocaoDTO.toDTO(feiraAdocao));
    }

    /**
     * Search.
     *
     * @param value     the value
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
                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        if (value != null) {
            var feirasAdocao = service.search(value, page, size, sort, direction).map(FeiraAdocaoDTO::toDTO);
            return ResponseEntity.status(OK).body(feirasAdocao);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update.
     *
     * @param id          the id
     * @param feiraAdocao the feira adocao
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid FeiraAdocao feiraAdocao) {

        if (feiraAdocao.getId().equals(id)) {
            feiraAdocao = service.save(feiraAdocao);
            return ResponseEntity.status(OK).body(FeiraAdocaoDTO.toDTO(feiraAdocao));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}