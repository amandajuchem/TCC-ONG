package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AnimalDTO;
import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.AnimalUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Animal controller.
 */
@RestController
@RequestMapping("/animais")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AnimalController {

    private final AnimalUtils animalUtils;
    private final FacadeService facade;

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        animalUtils.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> findAll() {

        var animaisDTO = facade.animalFindAll().stream()
                .map(AnimalDTO::toDTO)
                .toList();

        return ResponseEntity.status(OK).body(animaisDTO);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var animal = facade.animalFindById(id);
        var animalDTO = AnimalDTO.toDTO(animal);
        return ResponseEntity.status(OK).body(animalDTO);
    }

    /**
     * Save response entity.
     *
     * @param animal     the animal
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart @Valid Animal animal,
                               @RequestPart(required = false) MultipartFile novaFoto,
                               @RequestPart(required = false) String antigaFoto) {

        var animalSaved = animalUtils.save(animal, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
        var animalDTO = AnimalDTO.toDTO(animalSaved);

        return ResponseEntity.status(CREATED).body(animalDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String nome) {

        if (nome != null) {

            var animaisDTO = facade.animalFindByNomeContains(nome).stream()
                    .map(AnimalDTO::toDTO)
                    .toList();

            return ResponseEntity.status(OK).body(animaisDTO);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id         the id
     * @param animal     the animal
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the response entity
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable UUID id,
                                 @RequestPart @Valid Animal animal,
                                 @RequestPart(required = false) MultipartFile novaFoto,
                                 @RequestPart(required = false) String antigaFoto) {

        if (animal.getId().equals(id)) {

            var animalSaved = animalUtils.save(animal, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
            var animalDTO = AnimalDTO.toDTO(animalSaved);

            return ResponseEntity.status(OK).body(animalDTO);
        }

        throw new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND);
    }
}