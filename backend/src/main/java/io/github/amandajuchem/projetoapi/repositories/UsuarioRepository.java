package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Usuario repository.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Find by cpf optional.
     *
     * @param cpf the cpf
     * @return the optional
     */
    Optional<Usuario> findByCpf(String cpf);

    /**
     * Find by nome contains ignore case list.
     *
     * @param nome the nome
     * @return the list
     */
    List<Usuario> findByNomeContainsIgnoreCase(String nome);
}