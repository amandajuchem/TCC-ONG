package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.EmpresaDTO;
import io.github.amandajuchem.projetoapi.entities.Empresa;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.EmpresaService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
@Tag(name = "Empresas", description = "Endpoints for companies management")
public class EmpresaController implements AbstractController<Empresa, EmpresaDTO> {

    private final EmpresaService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(NOT_IMPLEMENTED)
    public void delete(@PathVariable UUID id) {
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<EmpresaDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public EmpresaDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public EmpresaDTO save(@RequestBody @Valid Empresa empresa) {
        return service.save(empresa);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<EmpresaDTO> search(@RequestParam String value,
                                   @RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                   @RequestParam(required = false, defaultValue = "nome") String sort,
                                   @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public EmpresaDTO update(@PathVariable UUID id, @RequestBody @Valid Empresa empresa) {

        if (empresa.getId().equals(id)) {
            return service.save(empresa);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}