package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.TutorDTO;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.github.amandajuchem.projetoapi.utils.TutorUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TutorController {

    private final FacadeService facade;
    private final TutorUtils tutorUtils;

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        tutorUtils.delete(id);
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
                                     @RequestParam(required = false, defaultValue = "nome") String sort,
                                     @RequestParam(required = false, defaultValue = "asc") String direction) {

        var tutores = facade.tutorFindAll(page, size, sort, direction).map(TutorDTO::toDTO);
        return ResponseEntity.status(OK).body(tutores);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var tutor = facade.tutorFindById(id);
        return ResponseEntity.status(OK).body(TutorDTO.toDTO(tutor));
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
    public ResponseEntity<?> save(@RequestPart @Valid Tutor tutor,
                                  @RequestPart(required = false) MultipartFile novaFoto,
                                  @RequestPart(required = false) String antigaFoto) {

        tutor = tutorUtils.save(tutor, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
        return ResponseEntity.status(CREATED).body(TutorDTO.toDTO(tutor));
    }

    /**
     * Search tutores.
     *
     * @param value     Nome, CPF ou RG
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of tutores
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        if (value != null) {
            var tutores = facade.tutorSearch(value, page, size, sort, direction).map(TutorDTO::toDTO);
            return ResponseEntity.status(OK).body(tutores);
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
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestPart @Valid Tutor tutor,
                                    @RequestPart(required = false) MultipartFile novaFoto,
                                    @RequestPart(required = false) String antigaFoto) {

        if (tutor.getId().equals(id)) {
            tutor = tutorUtils.save(tutor, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
            return ResponseEntity.status(OK).body(TutorDTO.toDTO(tutor));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}