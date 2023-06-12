package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Exame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExameRepository extends JpaRepository<Exame, UUID> {

    /**
     * Retrieves an exam entity by name case-insensitive.
     *
     * @param nome The name of the exam.
     * @return An Optional containing the exam entity if found, or an empty Optional if not found.
     */
    Optional<Exame> findByNomeIgnoreCase(String nome);

    /**
     * Search for exams by value.
     *
     * @param value The value to search for (name or category) case-insensitive.
     * @param page  The pageable object specifying the page information.
     * @return A page of exam entities matching the search criteria.
     */
    @Query(value = "SELECT e FROM tb_exames AS e " +
            "WHERE upper(e.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(e.categoria) LIKE upper(concat('%', ?1, '%'))")
    Page<Exame> search(String value, Pageable page);
}