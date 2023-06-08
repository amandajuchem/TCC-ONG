package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.FeiraAdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FeiraAdocaoService;
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
@RequestMapping("/feiras-adocao")
@RequiredArgsConstructor
@Tag(name = "Feira de Adoção", description = "Endpoints para gerenciamento de feiras de adoção")
public class FeiraAdocaoController implements AbstractController<FeiraAdocao, FeiraAdocaoDTO> {

    private final FeiraAdocaoService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<FeiraAdocaoDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "nome") String sort,
                                                        @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var feirasAdocao = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(feirasAdocao);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FeiraAdocaoDTO> findById(@PathVariable UUID id) {
        final var feiraAdocao = service.findById(id);
        return ResponseEntity.status(OK).body(feiraAdocao);
    }

    @Override
    @PostMapping
    public ResponseEntity<FeiraAdocaoDTO> save(@RequestBody @Valid FeiraAdocao feiraAdocao) {
        final var feiraAdocaoSaved = service.save(feiraAdocao);
        return ResponseEntity.status(CREATED).body(feiraAdocaoSaved);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<FeiraAdocaoDTO>> search(@RequestParam String value,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "nome") String sort,
                                                       @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var feirasAdocao = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(feirasAdocao);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FeiraAdocaoDTO> update(@PathVariable UUID id, @RequestBody @Valid FeiraAdocao feiraAdocao) {

        if (feiraAdocao.getId().equals(id)) {
            final var feiraAdocaoSaved = service.save(feiraAdocao);
            return ResponseEntity.status(OK).body(feiraAdocaoSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}