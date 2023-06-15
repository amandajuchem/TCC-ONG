package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.TutorDTO;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.TutorService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Controller class for managing tutors.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/tutores")
@RequiredArgsConstructor
@Tag(name = "Tutores", description = "Endpoints for tutors management")
public class TutorController implements AbstractController<Tutor, TutorDTO> {

    private final TutorService service;

    /**
     * Deletes a tutor by ID.
     *
     * @param id The ID of the tutor to be deleted.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieves all tutors.
     *
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of TutorDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<TutorDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                                  @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var tutores = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(tutores);
    }

    /**
     * Retrieves a tutor by ID.
     *
     * @param id The ID of the tutor to be retrieved.
     * @return A ResponseEntity containing the TutorDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> findById(@PathVariable UUID id) {
        final var tutor = service.findById(id);
        return ResponseEntity.status(OK).body(tutor);
    }

    /**
     * Saves a tutor.
     *
     * @param tutor The tutor to be saved.
     * @param foto  The profile photo of the tutor (optional).
     * @return A ResponseEntity containing the saved TutorDTO object, with the HTTP status of 201 (CREATED).
     * @throws FileNotFoundException if the profile photo file is not found.
     */
    @Operation(summary = "Save", description = "Saves a tutor")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TutorDTO> save(@RequestPart @Valid Tutor tutor,
                                         @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (foto != null) {

            final var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                    .build();

            tutor.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }

        return save(tutor);
    }

    /**
     * Saves a tutor.
     *
     * @param tutor The tutor to be saved.
     * @return A ResponseEntity containing the saved TutorDTO object, with the HTTP status of 201 (CREATED).
     */
    @Override
    public ResponseEntity<TutorDTO> save(Tutor tutor) {
        final var tutorSaved = service.save(tutor);
        return ResponseEntity.status(CREATED).body(tutorSaved);
    }

    /**
     * Search for tutors by value.
     *
     * @param value     The value to search for.
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of TutorDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<TutorDTO>> search(@RequestParam String value,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                 @RequestParam(required = false, defaultValue = "nome") String sort,
                                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var tutores = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(tutores);
    }

    /**
     * Updates a tutor.
     *
     * @param id    The ID of the tutor to be updated.
     * @param tutor The updated tutor.
     * @param foto  The updated profile photo of the tutor (optional).
     * @return A ResponseEntity containing the updated TutorDTO object, with HTTP status of 200 (OK).
     * @throws FileNotFoundException if the profile photo file is not found.
     * @throws ValidationException   If the provided tutor ID does not match the path ID.
     */
    @Operation(summary = "Update", description = "Updates a tutor")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TutorDTO> update(@PathVariable UUID id,
                                           @RequestPart @Valid Tutor tutor,
                                           @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (tutor.getId().equals(id)) {

            if (foto != null) {

                final var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                        .build();

                tutor.setFoto(imagem);
                FileUtils.FILES.put(imagem.getNome(), foto);
            }

            return update(id, tutor);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    /**
     * Updates a tutor.
     *
     * @param id    The ID of the tutor to be updated.
     * @param tutor The updated tutor.
     * @return A ResponseEntity containing the updated TutorDTO object, with HTTP status of 200 (OK).
     */
    @Override
    public ResponseEntity<TutorDTO> update(UUID id, Tutor tutor) {
        final var tutorSaved = service.save(tutor);
        return ResponseEntity.status(OK).body(tutorSaved);
    }
}