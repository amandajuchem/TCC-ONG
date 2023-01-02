package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.github.amandajuchem.projetoapi.utils.UsuarioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Usuario controller.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final FacadeService facade;
    private final UsuarioUtils usuarioUtils;

    /**
     * Instantiates a new Usuario controller.
     *
     * @param facade       the facade
     * @param usuarioUtils the usuario utils
     */
    @Autowired
    public UsuarioController(FacadeService facade, UsuarioUtils usuarioUtils) {
        this.facade = facade;
        this.usuarioUtils = usuarioUtils;
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity findAll() {

        return ResponseEntity.status(OK).body(facade.usuarioFindAll().stream()
                .filter(u -> !u.getCpf().equals("07905836584"))
                .toList());
    }

    /**
     * Save response entity.
     *
     * @param usuario    the usuario
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity save(@RequestPart @Valid Usuario usuario,
                               @RequestPart(required = false) MultipartFile novaFoto,
                               @RequestPart(required = false) String antigaFoto) {

        return ResponseEntity.status(CREATED).body(
                usuarioUtils.save(usuario, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null));
    }

    /**
     * Update response entity.
     *
     * @param id         the id
     * @param usuario    the usuario
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the response entity
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity update(@PathVariable UUID id,
                                 @RequestPart @Valid Usuario usuario,
                                 @RequestPart(required = false) MultipartFile novaFoto,
                                 @RequestPart(required = false) String antigaFoto) {

        if (usuario.getId().equals(id)) {

            return ResponseEntity.status(OK).body(
                    usuarioUtils.save(usuario, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null));
        }

        throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
    }
}