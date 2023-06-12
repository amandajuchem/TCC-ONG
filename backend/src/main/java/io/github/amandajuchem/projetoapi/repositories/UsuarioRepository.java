package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Retrieves a user by CPF.
     *
     * @param cpf The CPF of the user.
     * @return An Optional containing the user entity if found, or an empty Optional if not found.
     */
    Optional<Usuario> findByCpf(String cpf);

    /**
     * Search for users by value.
     *
     * @param value The value to search for (name, CPF, or setor) case-insensitive.
     * @param page  The pageable object specifying the page information.
     * @return A page of user entities matching the search criteria.
     */
    @Query(value = "SELECT u FROM tb_usuarios AS u " +
            "WHERE upper(u.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR u.cpf LIKE concat('%', ?1, '%') " +
            "OR upper(u.setor) LIKE upper(concat('%', ?1, '%'))")
    Page<Usuario> search(String value, Pageable page);
}
