package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
     * Search usuários.
     *
     * @param value Nome ou CPF
     * @param page  the page
     * @return the list of usuários
     */
    @Query(value = "SELECT u FROM tb_usuarios AS u " +
            "WHERE upper(u.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR u.cpf LIKE concat('%', ?1, '%') " +
            "OR upper(u.setor) LIKE upper(concat('%', ?1, '%'))")
    Page<Usuario> search(String value, Pageable page);
}