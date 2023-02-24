package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.UsuarioRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Usuario service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    /**
     * Find all usuários.
     *
     * @return the list of usuários
     */
    public Page<Usuario> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find usuário by CPF.
     *
     * @param cpf the cpf
     * @return the usuario
     */
    public Usuario findByCpf(String cpf) {

        return repository.findByCpf(cpf).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
        });
    }

    /**
     * Find usuário by ID.
     *
     * @param id the id
     * @return the usuario
     */
    public Usuario findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
        });
    }

    /**
     * Save usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public Usuario save(Usuario usuario) {

        if (usuario == null) {
            throw new ValidationException(MessageUtils.USUARIO_NULL);
        }

        if (validateUsuario(usuario)) {
            usuario = repository.save(usuario);
        }

        return usuario;
    }

    /**
     * Search usuários.
     *
     * @param value     Nome ou CPF
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of usuários
     */
    public Page<Usuario> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate usuario.
     *
     * @param usuario the usuario
     * @return the boolean
     */
    private boolean validateUsuario(Usuario usuario) {

        var usuario_findByCPF = repository.findByCpf(usuario.getCpf()).orElse(null);

        if (usuario_findByCPF != null && !usuario.equals(usuario_findByCPF)) {
            throw new ValidationException("Usuário já cadastrado!");
        }

        return true;
    }
}