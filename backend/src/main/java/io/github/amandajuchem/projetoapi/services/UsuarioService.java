package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.UsuarioDTO;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.UsuarioRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class that implements the AbstractService interface and UserDetailsService interface for managing user objects.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService implements AbstractService<Usuario, UsuarioDTO>, UserDetailsService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository repository;

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user object to be deleted.
     * @throws ObjectNotFoundException if the user object with the given ID is not found.
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
     * Encodes the password of the user object.
     *
     * @param usuario the user object to encode the password for.
     * @return the user object with the encoded password.
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario encodePassword(Usuario usuario) {

        if (usuario.getId() != null) {

            final var usuarioOld = repository.findById(usuario.getId()).get();

            if (!usuario.getSenha().equals(usuarioOld.getSenha())) {
                usuario.setSenha(encoder.encode(usuario.getSenha()));
            }
        } else {
            usuario.setSenha(encoder.encode(usuario.getSenha()));
        }

        return usuario;
    }

    /**
     * Retrieves all users.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested UsuarioDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UsuarioDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(UsuarioDTO::toDTO);
    }

    /**
     * Retrieves a user by CPF.
     *
     * @param cpf the CPF of the user object to be retrieved.
     * @return the UsuarioDTO representing the requested user object.
     * @throws ObjectNotFoundException if the user object with the given CPF is not found.
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UsuarioDTO findByCpf(String cpf) {
        final var usuario = repository.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND));
        return UsuarioDTO.toDTO(usuario);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user object to be retrieved.
     * @return the UsuarioDTO representing the requested user object.
     * @throws ObjectNotFoundException if the user object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UsuarioDTO findById(UUID id) {
        final var usuario = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND));
        return UsuarioDTO.toDTO(usuario);
    }

    /**
     * Loads a UserDetails object by username (in this case, the username is the CPF).
     *
     * @param username the username (CPF) of the user object to load.
     * @return the UserDetails object representing the requested user object.
     * @throws UsernameNotFoundException if the user object with the given CPF is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByCpf(username).orElseThrow(() -> new UsernameNotFoundException(MessageUtils.AUTHENTICATION_FAIL));
    }

    /**
     * Saves a user.
     *
     * @param usuario the user object to be saved.
     * @return the saved UsuarioDTO object.
     * @throws ValidationException if the user object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UsuarioDTO save(Usuario usuario) {

        if (usuario == null) {
            throw new ValidationException(MessageUtils.USUARIO_NULL);
        }

        if (validate(usuario)) {
            usuario = encodePassword(usuario);
            usuario = repository.save(usuario);
        }

        return UsuarioDTO.toDTO(usuario);
    }

    /**
     * Search for users by value.
     *
     * @param value     the value to search for (name, CPF, or setor) case-insensitive.
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested UsuarioDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UsuarioDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(UsuarioDTO::toDTO);
    }

    /**
     * Validates a user.
     *
     * @param usuario the user object to be validated.
     * @return true if the user object is valid.
     * @throws ValidationException if the user object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Usuario usuario) {

        final var usuarioFindByCPF = repository.findByCpf(usuario.getCpf()).orElse(null);

        if (usuarioFindByCPF != null && !usuario.equals(usuarioFindByCPF)) {
            throw new ValidationException("Usuário já cadastrado!");
        }

        return true;
    }
}