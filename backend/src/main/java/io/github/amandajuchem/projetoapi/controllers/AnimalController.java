package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AnimalDTO;
import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AnimalService;
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
 * Controller class for managing animals.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/animais")
@RequiredArgsConstructor
@Tag(name = "Animais", description = "Endpoints for animals management")
public class AnimalController implements AbstractController<Animal, AnimalDTO> {

    private final AnimalService service;

    /**
     * Delete an animal by ID.
     *
     * @param id The ID of the animal to be deleted.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieve all animals.
     *
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of AnimalDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<AnimalDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                                   @RequestParam(required = false, defaultValue = "nome") String sort,
                                                   @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var animais = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(animais);
    }

    /**
     * Retrieve an animal by ID.
     *
     * @param id The ID of the animal to be retrieved.
     * @return A ResponseEntity containing the AnimalDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable UUID id) {
        final var animal = service.findById(id);
        return ResponseEntity.status(OK).body(animal);
    }

    /**
     * Save an animal.
     *
     * @param animal The animal object to be saved.
     * @param foto   The MultipartFile object representing the photo of the animal (optional).
     * @return A ResponseEntity containing the saved AnimalDTO object, with the HTTP status of 201 (CREATED).
     * @throws FileNotFoundException if the photo file is not found.
     */
    @Operation(summary = "Save", description = "Saves an item")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimalDTO> save(@RequestPart @Valid Animal animal,
                                          @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (foto != null) {

            final var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                    .build();

            animal.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }

        return save(animal);
    }

    /**
     * Save an animal.
     *
     * @param animal The animal object to be saved.
     * @return A ResponseEntity containing the saved AnimalDTO object, with the HTTP status of 201 (CREATED).
     */
    @Override
    public ResponseEntity<AnimalDTO> save(Animal animal) {
        final var animalSaved = service.save(animal);
        return ResponseEntity.status(CREATED).body(animalSaved);
    }

    /**
     * Search for animals by value.
     *
     * @param value     The value to search for.
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of AnimalDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<AnimalDTO>> search(@RequestParam String value,
                                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                                  @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var animais = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(animais);
    }

    /**
     * Update an animal.
     *
     * @param id     The ID of the animal to be updated.
     * @param animal The updated animal object.
     * @param foto   The MultipartFile object representing the updated photo of the Animal (optional).
     * @return A ResponseEntity containing the updated AnimalDTO object, with HTTP status of 200 (OK).
     * @throws FileNotFoundException if the photo file is not found.
     * @throws ValidationException If the provided animal ID does not match the path ID.
     */
    @Operation(summary = "Update", description = "Updates an item")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimalDTO> update(@PathVariable UUID id,
                                            @RequestPart @Valid Animal animal,
                                            @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (animal.getId().equals(id)) {

            if (foto != null) {

                final var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                        .build();

                animal.setFoto(imagem);
                FileUtils.FILES.put(imagem.getNome(), foto);
            }

            return update(id, animal);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    /**
     * Update an animal.
     *
     * @param id     The ID of the animal to be updated.
     * @param animal The updated animal object.
     * @return A ResponseEntity containing the updated AnimalDTO object, with HTTP status of 200 (OK).
     */
    @Override
    public ResponseEntity<AnimalDTO> update(UUID id, Animal animal) {
        final var animalSaved = service.save(animal);
        return ResponseEntity.status(OK).body(animalSaved);
    }
}