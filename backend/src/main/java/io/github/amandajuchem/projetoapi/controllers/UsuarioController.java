package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.UsuarioDTO;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Usuario controller.
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    /**
     * Find all usuários.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of usuários
     */
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "nome") String sort,
                                     @RequestParam(required = false, defaultValue = "asc") String direction) {

        var usuarios = service.findAll(page, size, sort, direction).map(UsuarioDTO::toDTO);
        return ResponseEntity.status(OK).body(usuarios);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var usuario = service.findById(id);
        return ResponseEntity.status(OK).body(UsuarioDTO.toDTO(usuario));
    }

    /**
     * Save response entity.
     *
     * @param usuario the usuario
     * @param foto    the foto
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@RequestPart @Valid Usuario usuario,
                                  @RequestPart(required = false) MultipartFile foto) throws FileNotFoundException {

        if (foto != null) {

            var imagem = Imagem.builder()
                    .nome(System.currentTimeMillis() + "." + FileUtils.getExtension(foto))
                    .build();

            usuario.setFoto(imagem);
            FileUtils.FILES.put(imagem.getNome(), foto);
        }

        usuario = service.save(usuario);
        return ResponseEntity.status(CREATED).body(UsuarioDTO.toDTO(usuario));
    }

    /**
     * Search usuários.
     *
     * @param value     the nome ou CPF
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "nome") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        if (value != null) {
            var usuarios = service.search(value, page, size, sort, direction).map(UsuarioDTO::toDTO);
            return ResponseEntity.status(OK).body(usuarios);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id      the id
     * @param usuario the usuario
     * @param foto    the foto
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable UUID id,
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

            usuario = service.save(usuario);
            return ResponseEntity.status(OK).body(UsuarioDTO.toDTO(usuario));
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}