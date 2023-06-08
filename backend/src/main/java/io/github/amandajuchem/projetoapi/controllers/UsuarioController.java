package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.UsuarioDTO;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
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
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController implements AbstractController<Usuario, UsuarioDTO> {

    private final UsuarioService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return ResponseEntity.status(NOT_IMPLEMENTED).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var usuarios = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(usuarios);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable UUID id) {
        final var usuario = service.findById(id);
        return ResponseEntity.status(OK).body(usuario);
    }

    @Operation(summary = "Save", description = "Save an item")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioDTO> save(@RequestPart @Valid Usuario usuario,
                                           @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (foto != null) {

            var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                    .build();

            usuario.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }

        return save(usuario);
    }

    @Override
    public ResponseEntity<UsuarioDTO> save(Usuario usuario) {
        final var usuarioSaved = service.save(usuario);
        return ResponseEntity.status(CREATED).body(usuarioSaved);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<UsuarioDTO>> search(@RequestParam String value,
                                                   @RequestParam(required = false, defaultValue = "0") Integer page,
                                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                                   @RequestParam(required = false, defaultValue = "nome") String sort,
                                                   @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var usuarios = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(OK).body(usuarios);
    }

    @Operation(summary = "Update", description = "Update an item")
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioDTO> update(@PathVariable UUID id,
                                             @RequestPart @Valid Usuario usuario,
                                             @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (usuario.getId().equals(id)) {

            if (foto != null) {

                var imagem = Imagem.builder()
                        .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                        .build();

                usuario.setFoto(imagem);
                FileUtils.FILES.put(imagem.getNome(), foto);
            }

            return update(id, usuario);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    public ResponseEntity<UsuarioDTO> update(UUID id, Usuario usuario) {
        final var usuarioSaved = service.save(usuario);
        return ResponseEntity.status(OK).body(usuarioSaved);
    }
}