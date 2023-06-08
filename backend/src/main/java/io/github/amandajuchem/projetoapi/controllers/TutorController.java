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

@RestController
@RequestMapping("/tutores")
@RequiredArgsConstructor
@Tag(name = "Tutor", description = "Endpoints para gerenciamento de tutores")
public class TutorController implements AbstractController<Tutor, TutorDTO> {

    private final TutorService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<TutorDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                                  @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var tutores = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(tutores);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> findById(@PathVariable UUID id) {
        final var tutor = service.findById(id);
        return ResponseEntity.status(OK).body(tutor);
    }

    @Operation(summary = "Save", description = "Save an item")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TutorDTO> save(@RequestPart @Valid Tutor tutor,
                                         @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (foto != null) {

            var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                    .build();

            tutor.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }

        return save(tutor);
    }

    @Override
    public ResponseEntity<TutorDTO> save(Tutor tutor) {
        final var tutorSaved = service.save(tutor);
        return ResponseEntity.status(CREATED).body(tutorSaved);
    }

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

    @Operation(summary = "Update", description = "Update an item")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TutorDTO> update(@PathVariable UUID id,
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

            return update(id, tutor);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public ResponseEntity<TutorDTO> update(UUID id, Tutor tutor) {
        final var tutorSaved = service.save(tutor);
        return ResponseEntity.status(OK).body(tutorSaved);
    }
}