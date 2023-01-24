package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.UsuarioRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Usuario service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UsuarioService {

    private final UsuarioRepository repository;

    /**
     * Find all list.
     *
     * @return the list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    /**
     * Find by cpf usuario.
     *
     * @param cpf the cpf
     * @return the usuario
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario findByCpf(String cpf) {

        return repository.findByCpf(cpf).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
        });
    }

    /**
     * Find by id usuario.
     *
     * @param id the id
     * @return the usuario
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
    @Transactional(propagation = Propagation.REQUIRED)
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