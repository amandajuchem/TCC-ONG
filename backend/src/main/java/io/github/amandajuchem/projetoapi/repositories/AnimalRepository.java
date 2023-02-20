package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Animal repository.
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, UUID> {

    /**
     * Find by nome contains ignore case list.
     *
     * @param nome the nome
     * @param page the page
     * @return the list
     */
    Page<Animal> findByNomeContainsIgnoreCase(String nome, Pageable page);
}