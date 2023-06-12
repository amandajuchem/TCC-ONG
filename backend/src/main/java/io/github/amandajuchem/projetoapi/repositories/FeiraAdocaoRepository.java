package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeiraAdocaoRepository extends JpaRepository<FeiraAdocao, UUID> {

    /**
     * Retrieves an adoption fair by name case-insensitive.
     *
     * @param nome The name of the feira de adoção.
     * @return An Optional containing the adoption fair entity if found, or an empty Optional if not found.
     */
    Optional<FeiraAdocao> findByNomeIgnoreCase(String nome);

    /**
     * Search for adoption fairs by value.
     *
     * @param value The value to search for (name or date/time) case-insensitive.
     * @param page  The pageable object specifying the page information.
     * @return A page of adoption fair entities matching the search criteria.
     */
    @Query("SELECT f FROM tb_feiras_adocao AS f " +
            "WHERE upper(f.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR cast(f.dataHora as string) LIKE concat('%', ?1, '%')")
    Page<FeiraAdocao> search(String value, Pageable page);
}