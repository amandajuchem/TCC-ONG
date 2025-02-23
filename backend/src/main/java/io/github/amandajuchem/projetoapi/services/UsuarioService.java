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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService implements AbstractService<Usuario, UsuarioDTO>, UserDetailsService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {

        if (id != null) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND);
    }

    @Transactional
    public Usuario encodePassword(Usuario usuario) {
        if (usuario.getId() == null || !usuario.getSenha().equals(repository.findById(usuario.getId()).get().getSenha())) {
            usuario.setSenha(encoder.encode(usuario.getSenha()));
        }
        return usuario;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(UsuarioDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findByCpf(String cpf) {
        final var usuario = repository.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND));
        return UsuarioDTO.toDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(UUID id) {
        final var usuario = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USUARIO_NOT_FOUND));
        return UsuarioDTO.toDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByCpf(username).orElseThrow(() -> new UsernameNotFoundException(MessageUtils.AUTHENTICATION_FAIL));
    }

    @Override
    @Transactional
    public UsuarioDTO save(Usuario usuario) {
        usuario = encodePassword(usuario);
        usuario = repository.save(usuario);
        return UsuarioDTO.toDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(UsuarioDTO::toDTO);
    }

    @Override
    public void validate(Usuario usuario) {

        if (usuario == null) {
            throw new ValidationException(MessageUtils.USUARIO_NULL);
        }

        validateNome(usuario);
        validateCpf(usuario);
    }

    private void validateNome(Usuario usuario) {
        repository.findByNomeIgnoreCase(usuario.getNome())
                .ifPresent(existingUser -> {
                    if (!existingUser.equals(usuario)) {
                        throw new ValidationException("Tutor já cadastrado!");
                    }
                });
    }

    private void validateCpf(Usuario usuario) {
        repository.findByCpf(usuario.getCpf())
                .ifPresent(existingUser -> {
                    if (!existingUser.equals(usuario)) {
                        throw new ValidationException("CPF já cadastrado!");
                    }
                });
    }
}