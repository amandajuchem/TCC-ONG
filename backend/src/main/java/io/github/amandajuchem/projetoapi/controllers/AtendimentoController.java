package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AtendimentoDTO;
import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AtendimentoService;
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
 * Controller class for managing treatments.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
@Tag(name = "Atendimentos", description = "Endpoints for treatments management")
public class AtendimentoController implements AbstractController<Atendimento, AtendimentoDTO> {

    private final AtendimentoService service;

    /**
     * Deletes a treatment by its ID.
     *
     * @param id The ID of the treatment to delete.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Retrieves all treatments.
     *
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "dataHora").
     * @param direction The sorting direction (optional, default: "desc").
     * @return A ResponseEntity containing a page of AtendimentoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<AtendimentoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                        @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var atendimentos = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(atendimentos);
    }

    /**
     * Retrieves a treatment by ID.
     *
     * @param id The ID of the treatment to be retrieved.
     * @return A ResponseEntity containing the AtendimentoDTO object, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoDTO> findById(@PathVariable UUID id) {
        final var atendimento = service.findById(id);
        return ResponseEntity.status(OK).body(atendimento);
    }

    /**
     * Saves a treatment.
     *
     * @param atendimento The treatment to be saved.
     * @param documentos  The list of documents associated with the treatment (optional).
     * @return A ResponseEntity containing the saved AtendimentoDTO object, with the HTTP status of 201 (CREATED).
     * @throws FileNotFoundException If a file is not found.
     * @throws InterruptedException  If the thread is interrupted.
     */
    @Operation(summary = "Save", description = "Saves an item")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AtendimentoDTO> save(@RequestPart @Valid Atendimento atendimento,
                                               @RequestPart(required = false) List<MultipartFile> documentos) throws FileNotFoundException, InterruptedException {

        if (documentos != null) {

            for (MultipartFile documento : documentos) {

                final var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(documento))
                        .build();

                if (atendimento.getDocumentos() == null) {
                    atendimento.setDocumentos(new ArrayList<>());
                }

                atendimento.getDocumentos().add(imagem);
                FileUtils.FILES.put(imagem.getNome(), documento);
                Thread.sleep(10);
            }
        }

        return save(atendimento);
    }

    /**
     * Saves a treatment.
     *
     * @param atendimento The treatment to be saved.
     * @return A ResponseEntity containing the saved AtendimentoDTO object, with the HTTP status of 201 (CREATED).
     */
    @Override
    public ResponseEntity<AtendimentoDTO> save(Atendimento atendimento) {
        final var atendimentoSaved = service.save(atendimento);
        return ResponseEntity.status(CREATED).body(atendimentoSaved);
    }

    /**
     * Search for treatments by value.
     *
     * @param value     The value to search for.
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "dataHora").
     * @param direction The sorting direction (optional, default: "desc").
     * @return A ResponseEntity containing a page of AtendimentoDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<AtendimentoDTO>> search(@RequestParam String value,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                       @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var atendimentos = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(atendimentos);
    }

    /**
     * Updates a treatment.
     *
     * @param id          The ID of the treatment to be updated.
     * @param atendimento The updated treatment.
     * @param documentos  The list of documents associated with the treatment (optional).
     * @return A ResponseEntity containing the updated AtendimentoDTO object, with HTTP status of 200 (OK).
     * @throws FileNotFoundException If a file is not found.
     * @throws InterruptedException  If the thread is interrupted.
     * @throws ValidationException   If the provided treatment ID does not match the path ID.
     */
    @Operation(summary = "Update", description = "Updates an item")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AtendimentoDTO> update(@PathVariable UUID id,
                                                 @RequestPart @Valid Atendimento atendimento,
                                                 @RequestPart(required = false) List<MultipartFile> documentos) throws FileNotFoundException, InterruptedException {

        if (atendimento.getId().equals(id)) {

            if (documentos != null) {

                for (MultipartFile documento : documentos) {

                    final var imagem = Imagem.builder()
                            .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(documento))
                            .build();

                    if (atendimento.getDocumentos() == null) {
                        atendimento.setDocumentos(new ArrayList<>());
                    }

                    atendimento.getDocumentos().add(imagem);
                    FileUtils.FILES.put(imagem.getNome(), documento);
                    Thread.sleep(10);
                }
            }

            return update(id, atendimento);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    /**
     * Updates a treatment.
     *
     * @param id          The ID of the treatment to be updated.
     * @param atendimento The updated treatment.
     * @return A ResponseEntity containing the updated AtendimentoDTO object, with HTTP status of 200 (OK).
     */
    @Override
    public ResponseEntity<AtendimentoDTO> update(UUID id, Atendimento atendimento) {
        final var atendimentoSaved = service.save(atendimento);
        return ResponseEntity.status(OK).body(atendimentoSaved);
    }
}