package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    Optional<Tutor> findByNome(String nome);

    /**
     * Find by nome or cpf list.
     *
     * @param nome the nome
     * @return the list
     */
    List<Tutor> findByNomeContainsIgnoreCase(String nome);

    /**
     * Find by rg optional.
     *
     * @param rg the rg
     * @return the optional
     */
    Optional<Tutor> findByRg(String rg);
}