package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AtendimentoDTO;
import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.AtendimentoUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Atendimento controller.
 */
@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoUtils atendimentoUtils;
    private final FacadeService facade;

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        atendimentoUtils.delete(id);
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
                                     @RequestParam(required = false, defaultValue = "desc") String direction) {

        var atendimentos = facade.atendimentoFindAll(page, size, sort, direction).map(AtendimentoDTO::toDTO);
        return ResponseEntity.status(OK).body(atendimentos);
    }

    /**
     * Save response entity.
     *
     * @param atendimento        the atendimento
     * @param documentosToSave   the documentos to save
     * @param documentosToDelete the documentos to delete
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart @Valid Atendimento atendimento,
                                  @RequestPart(required = false) List<MultipartFile> documentosToSave,
                                  @RequestPart(required = false) List<UUID> documentosToDelete) {

        atendimento = atendimentoUtils.save(atendimento, documentosToSave, documentosToDelete);
        return ResponseEntity.status(CREATED).body(AtendimentoDTO.toDTO(atendimento));
    }

    /**
     * Search response entity.
     *
     * @param value     Data, nome do animal ou nome do veterinário
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of atendimentos
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                    @RequestParam(required = false, defaultValue = "desc") String direction) {

        if (value != null) {
            var atendimentos = facade.atendimentoSearch(value, page, size, sort, direction).map(AtendimentoDTO::toDTO);
            return ResponseEntity.status(OK).body(atendimentos);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id                 the id
     * @param atendimento        the atendimento
     * @param documentosToSave   the documentos to save
     * @param documentosToDelete the documentos to delete
     * @return the response entity
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestPart @Valid Atendimento atendimento,
                                    @RequestPart(required = false) List<MultipartFile> documentosToSave,
                                    @RequestPart(required = false) List<UUID> documentosToDelete) {

        if (atendimento.getId().equals(id)) {
            atendimento = atendimentoUtils.save(atendimento, documentosToSave, documentosToDelete);
            return ResponseEntity.status(OK).body(AtendimentoDTO.toDTO(atendimento));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}