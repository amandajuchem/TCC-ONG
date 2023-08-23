package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.UsuarioDTO;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
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
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints for users management")
public class UsuarioController implements AbstractController<Usuario, UsuarioDTO> {

    private final UsuarioService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<UsuarioDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public UsuarioDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioDTO save(@RequestBody @Valid Usuario usuario) {
        return service.save(usuario);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<UsuarioDTO> search(@RequestParam String value,
                                   @RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                   @RequestParam(required = false, defaultValue = "nome") String sort,
                                   @RequestParam(required = false, defaultValue = "asc") String direction) {
        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public UsuarioDTO update(@PathVariable UUID id, @RequestBody @Valid Usuario usuario) {

        if (usuario.getId().equals(id)) {
            return service.save(usuario);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}