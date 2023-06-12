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

/**
 * Controller class for managing users.
 * Provides endpoints for CRUD operations and searching.
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints for users management")
public class UsuarioController implements AbstractController<Usuario, UsuarioDTO> {

    private final UsuarioService service;

    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to be deleted.
     * @return A ResponseEntity with the HTTP status of 200 (OK).
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * Retrieves all users.
     *
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of UsuarioDTO objects, with the HTTP status of 200 (OK).
     */
    @Override
    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var usuarios = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(OK).body(usuarios);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return A ResponseEntity containing the UsuarioDTO object, with HTTP status of 200 (OK).
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable UUID id) {
        final var usuario = service.findById(id);
        return ResponseEntity.status(OK).body(usuario);
    }

    /**
     * Saves a user.
     *
     * @param usuario The user to be saved.
     * @param foto    The profile picture of the user (optional).
     * @return A ResponseEntity containing the saved UsuarioDTO object, with HTTP status of 201 (CREATED).
     * @throws FileNotFoundException If the file for the profile picture is not found.
     */
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

    /**
     * Saves a user.
     *
     * @param usuario The user to be saved.
     * @return A ResponseEntity containing the saved UsuarioDTO object, with HTTP status of 201 (CREATED).
     */
    @Override
    public ResponseEntity<UsuarioDTO> save(Usuario usuario) {
        final var usuarioSaved = service.save(usuario);
        return ResponseEntity.status(CREATED).body(usuarioSaved);
    }

    /**
     * Search for users by value.
     *
     * @param value     The value to search for.
     * @param page      The page number for pagination (optional, default: 0).
     * @param size      The page size for pagination (optional, default: 10).
     * @param sort      The sorting field (optional, default: "nome").
     * @param direction The sorting direction (optional, default: "asc").
     * @return A ResponseEntity containing a page of UsuarioDTO objects, with the HTTP status of 200 (OK).
     */
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

    /**
     * Updates a user.
     *
     * @param id       The ID of the user to be updated.
     * @param usuario  The updated user object.
     * @param foto     The updated profile picture of the user (optional).
     * @return A ResponseEntity containing the updated UsuarioDTO object, with HTTP status of 200 (OK).
     * @throws FileNotFoundException If the file for the updated profile picture is not found.
     * @throws ValidationException If the provided user ID does not match the path ID.
     */
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

    /**
     * Updates a user.
     *
     * @param id      The ID of the user to be updated.
     * @param usuario The updated user object.
     * @return A ResponseEntity containing the updated UsuarioDTO object, with HTTP status of 200 (OK).
     */
    @Override
    public ResponseEntity<UsuarioDTO> update(UUID id, Usuario usuario) {
        final var usuarioSaved = service.save(usuario);
        return ResponseEntity.status(OK).body(usuarioSaved);
    }
}