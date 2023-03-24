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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Usuario service.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService implements AbstractService<Usuario> {

    private final PasswordEncoder encoder;
    private final UsuarioRepository repository;

    /**
     * Delete usuário.
     *
     * @param id the id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
    }

    /**
     * Encode usuário password.
     *
     * @param usuario the usuário
     * @return the usuário
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario encodePassword(Usuario usuario) {

        if (usuario.getId() != null) {

            var usuario_old = repository.findById(usuario.getId()).get();

            if (!usuario.getSenha().equals(usuario_old.getSenha())) {
                usuario.setSenha(encoder.encode(usuario.getSenha()));
            }
        } else {
            usuario.setSenha(encoder.encode(usuario.getSenha()));
        }

        return usuario;
    }

    /**
     * Find all usuário.
     *
     * @return the usuário list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Usuario> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find usuário by CPF.
     *
     * @param cpf the cpf
     * @return the usuário
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario findByCpf(String cpf) {

        return repository.findByCpf(cpf).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
        });
    }

    /**
     * Find usuário by id.
     *
     * @param id the id
     * @return the usuário
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
        });
    }

    /**
     * Save usuário.
     *
     * @param usuario the usuário
     * @return the usuário
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario save(Usuario usuario) {

        if (usuario == null) {
            throw new ValidationException(MessageUtils.USUARIO_NULL);
        }

        if (validate(usuario)) {
            usuario = encodePassword(usuario);
            usuario = repository.save(usuario);
        }

        return usuario;
    }

    /**
     * Search usuário.
     *
     * @param value     the nome ou CPF
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the usuario list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Usuario> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate usuário.
     *
     * @param usuario the usuário
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Usuario usuario) {

        var usuario_findByCPF = repository.findByCpf(usuario.getCpf()).orElse(null);

        if (usuario_findByCPF != null && !usuario.equals(usuario_findByCPF)) {
            throw new ValidationException("Usuário já cadastrado!");
        }

        return true;
    }
}