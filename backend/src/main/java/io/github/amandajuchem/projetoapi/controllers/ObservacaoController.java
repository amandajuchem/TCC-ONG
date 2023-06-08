package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.ObservacaoDTO;
import io.github.amandajuchem.projetoapi.entities.Observacao;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.ObservacaoService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/observacoes")
@RequiredArgsConstructor
@Tag(name = "Observação", description = "Endpoints para gerenciamento de observações")
public class ObservacaoController implements AbstractController<Observacao, ObservacaoDTO> {

    private final ObservacaoService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ObservacaoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                                       @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var observacoes = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(observacoes);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ObservacaoDTO> findById(@PathVariable UUID id) {
        final var observacao = service.findById(id);
        return ResponseEntity.status(OK).body(observacao);
    }

    @Override
    @PostMapping
    public ResponseEntity<ObservacaoDTO> save(@RequestBody @Valid Observacao observacao) {
        final var observacaoSaved = service.save(observacao);
        return ResponseEntity.status(CREATED).body(observacaoSaved);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<ObservacaoDTO>> search(@RequestParam String value,
                                                      @RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, defaultValue = "10") Integer size,
                                                      @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                                      @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var observacoes = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(observacoes);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ObservacaoDTO> update(@PathVariable UUID id,
                                                @RequestBody @Valid Observacao observacao) {

        if (observacao.getId().equals(id)) {
            final var observacaoSaved = service.save(observacao);
            return ResponseEntity.status(OK).body(observacaoSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}