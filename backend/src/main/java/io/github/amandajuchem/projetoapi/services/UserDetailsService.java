package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type User details service.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            var usuario = service.findByCpf(username);

            return User.builder()
                    .username(usuario.getCpf())
                    .password(usuario.getSenha())
                    .enabled(usuario.getStatus())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getSetor().toString())))
                    .build();
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Usuário e/ou senha inválidos!");
        }
    }
}