package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Tutor repository.
 */
@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID> {

    /**
     * Find by cpf optional.
     *
     * @param cpf the cpf
     * @return the optional
     */
    Optional<Tutor> findByCpf(String cpf);

    /**
     * Find by nome optional.
     *
     * @param nome the nome
     * @return the optional
     */
    Optional<Tutor> findByNomeIgnoreCase(String nome);

    /**
     * Find by rg optional.
     *
     * @param rg the rg
     * @return the optional
     */
    Optional<Tutor> findByRg(String rg);

    /**
     * Search tutores.
     *
     * @param value Nome, CPF ou RG
     * @param page  the page
     * @return the list of tutores
     */
    @Query(value = "SELECT t FROM tb_tutores AS t " +
            "WHERE upper(t.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR t.cpf LIKE concat('%', ?1, '%') " +
            "OR t.rg LIKE concat('%', ?1, '%') ")
    Page<Tutor> search(String value, Pageable page);
}