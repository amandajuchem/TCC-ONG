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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
@Tag(name = "Exames", description = "Endpoints for exams management")
public class ExameController implements AbstractController<Exame, ExameDTO> {

    private final ExameService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<ExameDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "nome") String sort,
                                  @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ExameDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public ExameDTO save(@RequestBody @Valid Exame exame) {
        return service.save(exame);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<ExameDTO> search(@RequestParam String value,
                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "nome") String sort,
                                 @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public ExameDTO update(@PathVariable UUID id, @RequestBody @Valid Exame exame) {

        if (exame.getId().equals(id)) {
            return service.save(exame);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}