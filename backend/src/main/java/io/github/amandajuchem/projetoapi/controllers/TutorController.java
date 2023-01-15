package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.github.amandajuchem.projetoapi.utils.TutorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Tutor controller.
 */
@RestController
@RequestMapping("/tutores")
public class TutorController {

    private final FacadeService facade;
    private final TutorUtils tutorUtils;

    /**
     * Instantiates a new Tutor controller.
     *
     * @param facade     the facade
     * @param tutorUtils the tutor utils
     */
    @Autowired
    public TutorController(FacadeService facade, TutorUtils tutorUtils) {
        this.facade = facade;
        this.tutorUtils = tutorUtils;
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        facade.tutorDelete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.status(OK).body(facade.tutorFindAll());
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        return ResponseEntity.status(OK).body(facade.tutorFindById(id));
    }

    /**
     * Save response entity.
     *
     * @param tutor      the tutor
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity save(@RequestPart @Valid Tutor tutor,
                               @RequestPart(required = false) MultipartFile novaFoto,
                               @RequestPart(required = false) String antigaFoto) {

        return ResponseEntity.status(CREATED).body(
                tutorUtils.save(tutor, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null));
    }

    /**
     * Search response entity.
     *
     * @param nome the nome
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = false) String nome) {

        if (nome != null) {
            return ResponseEntity.status(OK).body(facade.tutorFindByNomeContains(nome));
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id         the id
     * @param tutor      the tutor
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the response entity
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity update(@PathVariable UUID id,
                                 @RequestPart @Valid Tutor tutor,
                                 @RequestPart(required = false) MultipartFile novaFoto,
                                 @RequestPart(required = false) String antigaFoto) {

        if (tutor.getId().equals(id)) {

            return ResponseEntity.status(OK).body(
                    tutorUtils.save(tutor, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null));
        }

        throw new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND);
    }
}