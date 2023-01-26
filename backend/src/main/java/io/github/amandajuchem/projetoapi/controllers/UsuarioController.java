package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.dtos.UsuarioDTO;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import io.github.amandajuchem.projetoapi.utils.UsuarioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Usuario controller.
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor(onConstructor_= {@Autowired})
public class UsuarioController {

    private final FacadeService facade;
    private final UsuarioUtils usuarioUtils;

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity findAll() {

        var usuariosDTO = facade.usuarioFindAll().stream()
                .filter(u -> !u.getCpf().equals("07905836584"))
                .map(u -> UsuarioDTO.toDTO(u))
                .toList();

        return ResponseEntity.status(OK).body(usuariosDTO);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        var usuario = facade.usuarioFindById(id);
        var usuarioDTO = UsuarioDTO.toDTO(usuario);
        return ResponseEntity.status(OK).body(usuarioDTO);
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

        var usuarioSaved = usuarioUtils.save(usuario, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
        var usuarioDTO = UsuarioDTO.toDTO(usuarioSaved);

        return ResponseEntity.status(CREATED).body(usuarioDTO);
    }

    /**
     * Search response entity.
     *
     * @param cpf the cpf
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = false) String cpf,
                                 @RequestParam(required = false) String nome) {

        if (cpf != null) {
            var usuario = facade.usuarioFindByCpf(cpf);
            var usuarioDTO = UsuarioDTO.toDTO(usuario);
            return ResponseEntity.status(OK).body(usuarioDTO);
        }

        if (nome != null) {

            var usuariosDTO = facade.usuarioFindByNomeContains(nome).stream()
                    .map(u -> UsuarioDTO.toDTO(u))
                    .toList();

            return ResponseEntity.status(OK).body(usuariosDTO);
        }

        return ResponseEntity.status(NOT_FOUND).body(null);
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

            var usuarioSaved = usuarioUtils.save(usuario, novaFoto, antigaFoto != null ? UUID.fromString(antigaFoto) : null);
            var usuarioDTO = UsuarioDTO.toDTO(usuarioSaved);

            return ResponseEntity.status(CREATED).body(usuarioDTO);
        }

        throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
    }
}