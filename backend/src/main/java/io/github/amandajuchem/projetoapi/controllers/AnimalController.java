package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AdocaoDTO;
import io.github.amandajuchem.projetoapi.dtos.AnimalDTO;
import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.AdocaoUtils;
import io.github.amandajuchem.projetoapi.utils.AnimalUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Animal controller.
 */
@RestController
@RequestMapping("/animais")
@RequiredArgsConstructor
public class AnimalController {

    private final AdocaoUtils adocaoUtils;
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
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "nome") String sort,
                                     @RequestParam(required = false, defaultValue = "asc") String direction) {

        var animais = facade.animalFindAll(page, size, sort, direction).map(AnimalDTO::toDTO);
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
        var animal = facade.animalFindById(id);
        return ResponseEntity.status(OK).body(AnimalDTO.toDTO(animal));
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

        animal = animalUtils.save(animal, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
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
     * @return the list of animais
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        if (value != null) {
            var animais = facade.animalSearch(value, page, size, sort, direction).map(AnimalDTO::toDTO);
            return ResponseEntity.status(OK).body(animais);
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
            animal = animalUtils.save(animal, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
            return ResponseEntity.status(OK).body(AnimalDTO.toDTO(animal));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    //////////////////////////////////////////////// ADOÇÃO ////////////////////////////////////////////////

    /**
     * Delete adoção.
     *
     * @param idAdocao the id adocao
     * @return the response entity
     */
    @DeleteMapping("/{id}/adocoes/{idAdocao}")
    public ResponseEntity<?> adocaoDelete(@PathVariable UUID idAdocao) {
        adocaoUtils.delete(idAdocao);
        return ResponseEntity.status(OK).body(null);
    }

    @GetMapping("/{id}/adocoes")
    public ResponseEntity<?> adocaoFindAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                           @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                           @RequestParam(required = false, defaultValue = "desc") String direction) {

        var adocoes = facade.adocaoFindAll(page, size, sort, direction).map(AdocaoDTO::toDTO);
        return ResponseEntity.status(OK).body(adocoes);
    }

    /**
     * Save adoção.
     *
     * @param id               the id
     * @param adocao           the adocao
     * @param documentosToSave the documentos to save
     * @return the response entity
     */
    @PostMapping("/{id}/adocoes")
    public ResponseEntity<?> adocaoSave(@PathVariable UUID id,
                                        @RequestPart @Valid Adocao adocao,
                                        @RequestPart(required = false) List<MultipartFile> documentosToSave) {

        if (adocao.getAnimal().getId().equals(id)) {
            adocao = adocaoUtils.save(adocao, documentosToSave, null);
            return ResponseEntity.status(CREATED).body(AdocaoDTO.toDTO(adocao));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    /**
     * Update adoção.
     *
     * @param id                 the id
     * @param idAdocao           the id adocao
     * @param adocao             the adocao
     * @param documentosToSave   the documentos to save
     * @param documentosToDelete the documentos to delete
     * @return the response entity
     */
    @PutMapping("/{id}/adocoes/{idAdocao}")
    public ResponseEntity<?> adocaoUpdate(@PathVariable UUID id,
                                          @PathVariable UUID idAdocao,
                                          @RequestPart @Valid Adocao adocao,
                                          @RequestPart(required = false) List<MultipartFile> documentosToSave,
                                          @RequestPart(required = false) List<UUID> documentosToDelete) {

        if (adocao.getAnimal().getId().equals(id)) {

            if (adocao.getId().equals(idAdocao)) {
                adocao = adocaoUtils.save(adocao, documentosToSave, documentosToDelete);
                return ResponseEntity.status(CREATED).body(AdocaoDTO.toDTO(adocao));
            }
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}