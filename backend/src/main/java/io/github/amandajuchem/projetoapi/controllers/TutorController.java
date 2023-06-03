package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.TutorDTO;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.TutorService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Tutor controller.
 */
@RestController
@RequestMapping("/tutores")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService service;

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

        var tutores = service.findAll(page, size, sort, direction).map(TutorDTO::toDTO);
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
        var tutor = service.findById(id);
        return ResponseEntity.status(OK).body(TutorDTO.toDTO(tutor));
    }

    /**
     * Save response entity.
     *
     * @param tutor the tutor
     * @param foto  the foto
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart @Valid Tutor tutor,
                                  @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (foto != null) {

            var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                    .build();

            tutor.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }

        tutor = service.save(tutor);
        return ResponseEntity.status(CREATED).body(TutorDTO.toDTO(tutor));
    }

    /**
     * Search tutor.
     *
     * @param value     the nome, CPF ou RG
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
            var tutores = service.search(value, page, size, sort, direction).map(TutorDTO::toDTO);
            return ResponseEntity.status(OK).body(tutores);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id    the id
     * @param tutor the tutor
     * @param foto  the foto
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestPart @Valid Tutor tutor,
                                    @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (tutor.getId().equals(id)) {

            if (foto != null) {

                var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                        .build();

                tutor.setFoto(imagem);
                FileUtils.FILES.put(imagem.getNome(), foto);
            }

            tutor = service.save(tutor);
            return ResponseEntity.status(OK).body(TutorDTO.toDTO(tutor));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}