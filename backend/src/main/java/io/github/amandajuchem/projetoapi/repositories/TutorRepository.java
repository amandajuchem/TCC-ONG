package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID> {

    /**
     * Retrieves a tutor by CPF.
     *
     * @param cpf The CPF of the tutor.
     * @return An Optional containing the tutor entity if found, or an empty Optional if not found.
     */
    Optional<Tutor> findByCpf(String cpf);

    /**
     * Retrieves a tutor by name case-insensitive.
     *
     * @param nome The name of the tutor.
     * @return An Optional containing the tutor entity if found, or an empty Optional if not found.
     */
    Optional<Tutor> findByNomeIgnoreCase(String nome);

    /**
     * Retrieves a tutor by RG.
     *
     * @param rg The RG of the tutor.
     * @return An Optional containing the tutor entity if found, or an empty Optional if not found.
     */
    Optional<Tutor> findByRg(String rg);

    /**
     * Search for tutors by value.
     *
     * @param value The value to search for (name, CPF, or RG) case-insensitive.
     * @param page  The pageable object specifying the page information.
     * @return A page of tutor entities matching the search criteria.
     */
    @Query(value = "SELECT t FROM tb_tutores AS t " +
            "WHERE upper(t.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR t.cpf LIKE concat('%', ?1, '%') " +
            "OR t.rg LIKE concat('%', ?1, '%') ")
    Page<Tutor> search(String value, Pageable page);
}