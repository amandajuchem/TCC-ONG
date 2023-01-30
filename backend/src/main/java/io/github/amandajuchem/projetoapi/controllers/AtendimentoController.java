package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AtendimentoDTO;
import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.AtendimentoUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<?> findAll() {

        var atendimentosDTO = facade.atendimentoFindAll().stream()
                .map(AtendimentoDTO::toDTO)
                .toList();

        return ResponseEntity.status(OK).body(atendimentosDTO);
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

        var atendimentoSaved = atendimentoUtils.save(atendimento, documentosToSave, documentosToDelete);
        var atendimentoDTO = AtendimentoDTO.toDTO(atendimentoSaved);

        return ResponseEntity.status(CREATED).body(atendimentoDTO);
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

            var atendimentoSaved = atendimentoUtils.save(atendimento, documentosToSave, documentosToDelete);
            var atendimentoDTO = AtendimentoDTO.toDTO(atendimentoSaved);

            return ResponseEntity.status(OK).body(atendimentoDTO);
        }

        throw new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND);
    }
}