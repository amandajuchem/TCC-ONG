package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final FacadeService facade;

    /**
     * Instantiates a new Animal controller.
     *
     * @param facade the facade
     */
    @Autowired
    public AnimalController(FacadeService facade) {
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
     * Save response entity.
     *
     * @param animal the animal
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid Animal animal) {
        return ResponseEntity.status(CREATED).body(facade.animalSave(animal));
    }
}