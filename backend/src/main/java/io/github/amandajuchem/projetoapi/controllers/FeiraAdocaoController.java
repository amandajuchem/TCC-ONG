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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/feiras-adocao")
@RequiredArgsConstructor
@Tag(name = "Feiras de Adoção", description = "Endpoints for adoption fairs management")
public class FeiraAdocaoController implements AbstractController<FeiraAdocao, FeiraAdocaoDTO> {

    private final FeiraAdocaoService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<FeiraAdocaoDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                        @RequestParam(required = false, defaultValue = "nome") String sort,
                                        @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public FeiraAdocaoDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public FeiraAdocaoDTO save(@RequestBody @Valid FeiraAdocao feiraAdocao) {
        return service.save(feiraAdocao);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<FeiraAdocaoDTO> search(@RequestParam String value,
                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                       @RequestParam(required = false, defaultValue = "nome") String sort,
                                       @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public FeiraAdocaoDTO update(@PathVariable UUID id, @RequestBody @Valid FeiraAdocao feiraAdocao) {

        if (feiraAdocao.getId().equals(id)) {
            return service.save(feiraAdocao);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}