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

@Service
@RequiredArgsConstructor
public class UsuarioService implements AbstractService<Usuario, UsuarioDTO>, UserDetailsService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository repository;

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

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario encodePassword(Usuario usuario) {

        if (usuario.getId() != null) {

            final var usuario_old = repository.findById(usuario.getId()).get();

            if (!usuario.getSenha().equals(usuario_old.getSenha())) {
                usuario.setSenha(encoder.encode(usuario.getSenha()));
            }
        } else {
            usuario.setSenha(encoder.encode(usuario.getSenha()));
        }

        return usuario;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UsuarioDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(UsuarioDTO::toDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UsuarioDTO findByCpf(String cpf) {
        final var usuario = repository.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND));
        return UsuarioDTO.toDTO(usuario);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UsuarioDTO findById(UUID id) {
        final var usuario = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND));
        return UsuarioDTO.toDTO(usuario);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByCpf(username).orElseThrow(() -> new UsernameNotFoundException(MessageUtils.AUTHENTICATION_FAIL));
    }

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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UsuarioDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(UsuarioDTO::toDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Usuario usuario) {

        final var usuario_findByCPF = repository.findByCpf(usuario.getCpf()).orElse(null);

        if (usuario_findByCPF != null && !usuario.equals(usuario_findByCPF)) {
            throw new ValidationException("Usuário já cadastrado!");
        }

        return true;
    }
}