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

    Optional<Exame> findByNomeIgnoreCase(String nome);

    @Query(value = "SELECT e FROM tb_exames AS e " +
                   "WHERE upper(e.nome) LIKE upper(concat('%', ?1, '%')) " +
                   "OR upper(e.categoria) LIKE upper(concat('%', ?1, '%'))")
    Page<Exame> search(String value, Pageable page);
}