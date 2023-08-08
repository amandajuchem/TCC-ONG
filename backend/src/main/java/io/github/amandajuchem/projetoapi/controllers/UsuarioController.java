package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.UsuarioDTO;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Objects;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public UsuarioDTO save(@RequestPart @Valid Usuario usuario,
                           @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {
        handleFoto(usuario, foto);
        return save(usuario);
    }

    @Override
    public UsuarioDTO save(Usuario usuario) {
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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    public UsuarioDTO update(@PathVariable UUID id,
                             @RequestPart @Valid Usuario usuario,
                             @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {
        if (usuario.getId().equals(id)) {
            handleFoto(usuario, foto);
            return update(id, usuario);
        }
        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public UsuarioDTO update(UUID id, Usuario usuario) {
        return service.save(usuario);
    }

    private void handleFoto(Usuario usuario, MultipartFile foto) {

        if (foto != null) {

            final var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(Objects.requireNonNull(foto.getOriginalFilename())))
                    .build();

            usuario.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }
    }
}