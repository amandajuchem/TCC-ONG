package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.AtendimentoDTO;
import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AtendimentoService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Atendimento controller.
 */
@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService service;

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
                                     @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                     @RequestParam(required = false, defaultValue = "desc") String direction) {

        var atendimentos = service.findAll(page, size, sort, direction).map(AtendimentoDTO::toDTO);
        return ResponseEntity.status(OK).body(atendimentos);
    }

    /**
     * Save response entity.
     *
     * @param atendimento the atendimento
     * @param documentos  the documentos
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     * @throws InterruptedException  the interrupted exception
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart @Valid Atendimento atendimento,
                                  @RequestPart(required = false) List<MultipartFile> documentos) throws FileNotFoundException, InterruptedException {

        if (documentos != null) {

            for (MultipartFile documento : documentos) {

                var imagem = Imagem.builder()
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

        atendimento = service.save(atendimento);
        return ResponseEntity.status(CREATED).body(AtendimentoDTO.toDTO(atendimento));
    }

    /**
     * Search response entity.
     *
     * @param value     the data, nome do animal ou nome do veterinário
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
                                    @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                    @RequestParam(required = false, defaultValue = "desc") String direction) {

        if (value != null) {
            var atendimentos = service.search(value, page, size, sort, direction).map(AtendimentoDTO::toDTO);
            return ResponseEntity.status(OK).body(atendimentos);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id          the id
     * @param atendimento the atendimento
     * @param documentos  the documentos
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     * @throws InterruptedException  the interrupted exception
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestPart @Valid Atendimento atendimento,
                                    @RequestPart(required = false) List<MultipartFile> documentos) throws FileNotFoundException, InterruptedException {

        if (atendimento.getId().equals(id)) {

            if (documentos != null) {

                for (MultipartFile documento : documentos) {

                    var imagem = Imagem.builder()
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

            atendimento = service.save(atendimento);
            return ResponseEntity.status(OK).body(AtendimentoDTO.toDTO(atendimento));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}