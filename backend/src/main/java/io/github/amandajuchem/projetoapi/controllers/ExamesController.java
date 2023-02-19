package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.ExameDTO;
import io.github.amandajuchem.projetoapi.entities.Exame;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Exames controller.
 */
@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExamesController {

    private final FacadeService facade;

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        facade.exameDelete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll() {

        var examesDTO = facade.exameFindAll().stream()
                .map(ExameDTO::toDTO)
                .toList();

        return ResponseEntity.status(OK).body(examesDTO);
    }

    /**
     * Save response entity.
     *
     * @param exame the exame
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Exame exame) {

        var exameSaved = facade.exameSave(exame);
        var exameDTO = ExameDTO.toDTO(exameSaved);

        return ResponseEntity.status(CREATED).body(exameDTO);
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

            var exameSaved = facade.exameSave(exame);
            var exameDTO = ExameDTO.toDTO(exameSaved);

            return ResponseEntity.status(OK).body(exameDTO);
        }

        throw new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND);
    }
}