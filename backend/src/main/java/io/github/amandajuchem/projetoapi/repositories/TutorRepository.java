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
     * Find tutor by cpf.
     *
     * @param cpf the cpf
     * @return the tutor optional
     */
    Optional<Tutor> findByCpf(String cpf);

    /**
     * Find tutor by nome.
     *
     * @param nome the nome
     * @return the tutor optional
     */
    Optional<Tutor> findByNomeIgnoreCase(String nome);

    /**
     * Find tutor by rg.
     *
     * @param rg the rg
     * @return the tutor optional
     */
    Optional<Tutor> findByRg(String rg);

    /**
     * Search tutor.
     *
     * @param value the nome, CPF ou RG
     * @param page  the page
     * @return the tutor list
     */
    @Query(value = "SELECT t FROM tb_tutores AS t " +
            "WHERE upper(t.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR t.cpf LIKE concat('%', ?1, '%') " +
            "OR t.rg LIKE concat('%', ?1, '%') ")
    Page<Tutor> search(String value, Pageable page);
}