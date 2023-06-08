package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.ExameDTO;
import io.github.amandajuchem.projetoapi.entities.Exame;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.ExameService;
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
@RequestMapping("/exames")
@RequiredArgsConstructor
@Tag(name = "Exame", description = "Endpoints para gerenciamento de exames")
public class ExamesController implements AbstractController<Exame, ExameDTO> {

    private final ExameService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ExameDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                                  @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var exames = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(exames);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ExameDTO> findById(@PathVariable UUID id) {
        final var exame = service.findById(id);
        return ResponseEntity.status(OK).body(exame);
    }

    @Override
    @PostMapping
    public ResponseEntity<ExameDTO> save(@RequestBody @Valid Exame exame) {
        final var exameSaved = service.save(exame);
        return ResponseEntity.status(CREATED).body(exameSaved);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<ExameDTO>> search(@RequestParam String value,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                 @RequestParam(required = false, defaultValue = "nome") String sort,
                                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var exames = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(exames);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ExameDTO> update(@PathVariable UUID id, @RequestBody @Valid Exame exame) {

        if (exame.getId().equals(id)) {
            final var exameSaved = service.save(exame);
            return ResponseEntity.status(OK).body(exameSaved);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}