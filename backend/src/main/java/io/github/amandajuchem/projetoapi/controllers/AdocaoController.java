package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AdocaoService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Controller class for managing adoptions.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/adocoes")
@RequiredArgsConstructor
@Tag(name = "Adoções", description = "Endpoints for adoptions management")
public class AdocaoController implements AbstractController<Adocao, AdocaoDTO> {

    private final AdocaoService service;

    /**
     * Deletes an adoption by ID.
     *
     * @param id The ID of the adoption to delete.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieve all adoptions.
     *
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "dataHora").
     * @param direction The sorting direction (optional, default: "desc").
     * @return A ResponseEntity containing a page of AdocaoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<AdocaoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                                   @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                   @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var adocoes = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(adocoes);
    }

    /**
     * Retrieve an adoption by ID.
     *
     * @param id The ID of the adoption to be retrieved.
     * @return A ResponseEntity containing the AdocaoDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AdocaoDTO> findById(@PathVariable UUID id) {
        final var adocao = service.findById(id);
        return ResponseEntity.status(OK).body(adocao);
    }

    /**
     * Saves an adoption.
     *
     * @param adocao                The adoption object to be saved.
     * @param termoResponsabilidade The list of termoResponsabilidade files (optional).
     * @return A ResponseEntity containing the saved AdocaoDTO object, with the HTTP status of 201 (CREATED).
     * @throws FileNotFoundException if the termoResponsabilidade file is not found.
     * @throws InterruptedException  if there is an interruption while processing the files.
     */
    @Operation(summary = "Save", description = "Saves an item")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdocaoDTO> save(@RequestPart @Valid Adocao adocao,
                                          @RequestPart(required = false) List<MultipartFile> termoResponsabilidade) throws FileNotFoundException, InterruptedException {

        if (termoResponsabilidade != null) {

            for (MultipartFile termo : termoResponsabilidade) {

                var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(termo))
                        .build();

                if (adocao.getTermoResponsabilidade() == null) {
                    adocao.setTermoResponsabilidade(new ArrayList<>());
                }

                adocao.getTermoResponsabilidade().add(imagem);
                FileUtils.FILES.put(imagem.getNome(), termo);
                Thread.sleep(10);
            }
        }

        return save(adocao);
    }

    /**
     * Save an adoption.
     *
     * @param adocao The adoption object to be saved.
     * @return A ResponseEntity containing the saved AdocaoDTO object, with HTTP status of 201 (CREATED).
     */
    @Override
    public ResponseEntity<AdocaoDTO> save(Adocao adocao) {
        final var adocaoSaved = service.save(adocao);
        return ResponseEntity.status(CREATED).body(adocaoSaved);
    }

    /**
     * Search for adoptions by value.
     *
     * @param value     The value to search for.
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "dataHora").
     * @param direction The sorting direction (optional, default: "desc").
     * @return A ResponseEntity containing a page of AdocaoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<AdocaoDTO>> search(@RequestParam String value,
                                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                  @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var adocoes = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(adocoes);
    }

    /**
     * Updates an adoption.
     *
     * @param id                    The ID of the adoption to be updated.
     * @param adocao                The updated adoption object.
     * @param termoResponsabilidade The list of responsibility term files (optional).
     * @return A ResponseEntity containing the updated AdocaoDTO object, with HTTP status of 200 (OK).
     * @throws FileNotFoundException if the termoResponsabilidade file is not found.
     * @throws InterruptedException  if there is an interruption while processing the files.
     * @throws ValidationException   If the provided adoption ID does not match the path ID.
     */
    @Operation(summary = "Update", description = "Updates an item")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdocaoDTO> update(@PathVariable UUID id,
                                            @RequestPart @Valid Adocao adocao,
                                            @RequestPart(required = false) List<MultipartFile> termoResponsabilidade) throws FileNotFoundException, InterruptedException {

        if (adocao.getId().equals(id)) {

            if (termoResponsabilidade != null) {

                for (MultipartFile termo : termoResponsabilidade) {

                    var imagem = Imagem.builder()
                            .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(termo))
                            .build();

                    if (adocao.getTermoResponsabilidade() == null) {
                        adocao.setTermoResponsabilidade(new ArrayList<>());
                    }

                    adocao.getTermoResponsabilidade().add(imagem);
                    FileUtils.FILES.put(imagem.getNome(), termo);
                    Thread.sleep(10);
                }
            }

            return update(id, adocao);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    /**
     * Updates an adoption.
     *
     * @param id      The ID of the adocao to be updated.
     * @param adocao  The updated adocao object.
     * @return A ResponseEntity containing the updated AdocaoDTO object, with HTTP status of 200 (OK).
     */
    @Override
    public ResponseEntity<AdocaoDTO> update(UUID id, Adocao adocao) {
        final var adocaoSaved = service.save(adocao);
        return ResponseEntity.status(OK).body(adocaoSaved);
    }
}