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

@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
@Tag(name = "Atendimento", description = "Endpoints para gerenciamento de atendimentos")
public class AtendimentoController implements AbstractController<Atendimento, AtendimentoDTO> {

    private final AtendimentoService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AtendimentoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "dataHora") String sort,
                                                        @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var atendimentos = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(atendimentos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoDTO> findById(@PathVariable UUID id) {
        final var atendimento = service.findById(id);
        return ResponseEntity.status(OK).body(atendimento);
    }

    @Operation(summary = "Save", description = "Save an item")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AtendimentoDTO> save(@RequestPart @Valid Atendimento atendimento,
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

        return save(atendimento);
    }

    @Override
    public ResponseEntity<AtendimentoDTO> save(Atendimento atendimento) {
        final var atendimentoSaved = service.save(atendimento);
        return ResponseEntity.status(CREATED).body(atendimentoSaved);
    }

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

    @Operation(summary = "Update", description = "Update an item")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AtendimentoDTO> update(@PathVariable UUID id,
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

            return update(id, atendimento);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public ResponseEntity<AtendimentoDTO> update(UUID id, Atendimento atendimento) {
        final var atendimentoSaved = service.save(atendimento);
        return ResponseEntity.status(OK).body(atendimentoSaved);
    }
}