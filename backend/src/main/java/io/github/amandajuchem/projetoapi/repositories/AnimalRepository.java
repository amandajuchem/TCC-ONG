package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Animal repository.
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, UUID> {

    /**
     * Search animal.
     *
     * @param value the nome
     * @param page  the page
     * @return the animal list
     */
    @Query(value = "SELECT a FROM tb_animais AS a WHERE upper(a.nome) LIKE upper(concat('%', ?1, '%'))")
    Page<Animal> search(String value, Pageable page);
}