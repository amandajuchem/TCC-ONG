package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.AnimalUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Animal controller.
 */
@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalUtils animalUtils;
    private final FacadeService facade;

    /**
     * Instantiates a new Animal controller.
     *
     * @param animalUtils the animal utils
     * @param facade      the facade
     */
    @Autowired
    public AnimalController(AnimalUtils animalUtils, FacadeService facade) {
        this.animalUtils = animalUtils;
        this.facade = facade;
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        facade.animalDelete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.status(OK).body(facade.animalFindAll());
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        return ResponseEntity.status(OK).body(facade.animalFindById(id));
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
    public ResponseEntity save(@RequestPart @Valid Animal animal,
                               @RequestPart(required = false) MultipartFile novaFoto,
                               @RequestPart(required = false) String antigaFoto) {

        return ResponseEntity.status(CREATED).body(
                animalUtils.save(animal, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null));
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
    public ResponseEntity update(@PathVariable UUID id,
                                 @RequestPart @Valid Animal animal,
                                 @RequestPart(required = false) MultipartFile novaFoto,
                                 @RequestPart(required = false) String antigaFoto) {

        if (animal.getId().equals(id)) {

            return ResponseEntity.status(OK).body(
                    animalUtils.save(animal, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null));
        }

        throw new ObjectNotFoundException(MessageUtils.ANIMAL_NOT_FOUND);
    }
}