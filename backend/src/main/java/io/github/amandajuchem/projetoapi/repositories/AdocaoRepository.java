package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Adocao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Adocao repository.
 */
@Repository
public interface AdocaoRepository extends JpaRepository<Adocao, UUID> {

    /**
     * Find all.
     *
     * @param animalId the animal id
     * @param tutorId  the tutor id
     * @param page     the page
     * @return
     */
    @Query("SELECT ad FROM tb_adocoes AS ad INNER JOIN ad.animal AS an INNER JOIN ad.tutor AS t ON an.id = ?1 OR t.id = ?2")
    Page<Adocao> findAll(UUID animalId, UUID tutorId, Pageable page);
}