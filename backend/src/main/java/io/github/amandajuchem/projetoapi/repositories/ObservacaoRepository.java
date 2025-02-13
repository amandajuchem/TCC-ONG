package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Observacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ObservacaoRepository extends JpaRepository<Observacao, UUID> {

    /**
     * Search for observations by value.
     *
     * @param value The value to search for (tutor's ID).
     * @param page  The pageable object specifying the page information.
     * @return A page of observation entities matching the search criteria.
     */
    @Query("SELECT o FROM tb_observacoes AS o " +
            "INNER JOIN o.tutor AS t " +
            "WHERE cast(t.id as string) = ?1")
    Page<Observacao> search(String value, Pageable page);
}