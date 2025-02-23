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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/observacoes")
@RequiredArgsConstructor
@Tag(name = "Observações", description = "Endpoints for observations management")
public class ObservacaoController implements AbstractController<Observacao, ObservacaoDTO> {

    private final ObservacaoService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<ObservacaoDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                       @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                       @RequestParam(required = false, defaultValue = "desc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ObservacaoDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public ObservacaoDTO save(@RequestBody @Valid Observacao observacao) {
        return service.save(observacao);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<ObservacaoDTO> search(@RequestParam String value,
                                      @RequestParam(required = false, defaultValue = "0") Integer page,
                                      @RequestParam(required = false, defaultValue = "10") Integer size,
                                      @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                      @RequestParam(required = false, defaultValue = "desc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public ObservacaoDTO update(@PathVariable UUID id,
                                @RequestBody @Valid Observacao observacao) {

        if (observacao.getId().equals(id)) {
            return service.save(observacao);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}