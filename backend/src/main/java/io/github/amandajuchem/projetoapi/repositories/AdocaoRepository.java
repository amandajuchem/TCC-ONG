package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Adocao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdocaoRepository extends JpaRepository<Adocao, UUID> {

    /**
     * Search for adoptions by value.
     *
     * @param value The value to search for (animal's ID or tutor's ID).
     * @param page  The pageable object specifying the page information.
     * @return A page of adoption entities matching the search criteria.
     */
    @Query("SELECT ad FROM tb_adocoes AS ad " +
            "INNER JOIN ad.animal AS an " +
            "INNER JOIN ad.tutor AS t " +
            "ON cast(an.id as string) = ?1 " +
            "OR cast(t.id as string) = ?1")
    Page<Adocao> search(String value, Pageable page);
}