package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.ExameDTO;
import io.github.amandajuchem.projetoapi.entities.Exame;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.ExameService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Exames controller.
 */
@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExamesController {

    private final ExameService service;

    /**
     * Delete response entity.
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
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                     @RequestParam(required = false, defaultValue = "asc") String direction) {

        var exames = service.findAll(page, size, sort, direction).map(ExameDTO::toDTO);
        return ResponseEntity.status(OK).body(exames);
    }

    /**
     * Save response entity.
     *
     * @param exame the exame
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Exame exame) {
        exame = service.save(exame);
        return ResponseEntity.status(CREATED).body(ExameDTO.toDTO(exame));
    }

    /**
     * Search exames.
     *
     * @param value     Nome ou categoria
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of exames
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        if (value != null) {
            var exames = service.search(value, page, size, sort, direction).map(ExameDTO::toDTO);
            return ResponseEntity.status(OK).body(exames);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id    the id
     * @param exame the exame
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid Exame exame) {

        if (exame.getId().equals(id)) {
            exame = service.save(exame);
            return ResponseEntity.status(OK).body(ExameDTO.toDTO(exame));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}