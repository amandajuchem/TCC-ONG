package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Exame repository.
 */
@Repository
public interface ExameRepository extends JpaRepository<Exame, UUID> {

    /**
     * Find exame by nome.
     *
     * @param nome the nome
     * @return the optional
     */
    Optional<Exame> findByNomeIgnoreCase(String nome);
}