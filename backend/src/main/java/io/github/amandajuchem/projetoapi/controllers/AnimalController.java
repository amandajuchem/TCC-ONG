package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AnimalDTO;
import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AnimalService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Animal controller.
 */
@RestController
@RequestMapping("/animais")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService service;

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

        var animais = service.findAll(page, size, sort, direction).map(AnimalDTO::toDTO);
        return ResponseEntity.status(OK).body(animais);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var animal = service.findById(id);
        return ResponseEntity.status(OK).body(AnimalDTO.toDTO(animal));
    }

    /**
     * Save response entity.
     *
     * @param animal the animal
     * @param foto   the foto
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart @Valid Animal animal,
                                  @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (foto != null) {

            var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                    .build();

            animal.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }

        animal = service.save(animal);
        return ResponseEntity.status(CREATED).body(AnimalDTO.toDTO(animal));
    }

    /**
     * Search response entity.
     *
     * @param value     Nome
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
            var animais = service.search(value, page, size, sort, direction).map(AnimalDTO::toDTO);
            return ResponseEntity.status(OK).body(animais);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id     the id
     * @param animal the animal
     * @param foto   the foto
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestPart @Valid Animal animal,
                                    @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (animal.getId().equals(id)) {

            if (foto != null) {

                var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                        .build();

                animal.setFoto(imagem);
                FileUtils.FILES.put(imagem.getNome(), foto);
            }

            animal = service.save(animal);
            return ResponseEntity.status(OK).body(AnimalDTO.toDTO(animal));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}