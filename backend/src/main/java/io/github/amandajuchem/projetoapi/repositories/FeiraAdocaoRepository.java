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

    Optional<FeiraAdocao> findByNomeIgnoreCase(String nome);

    @Query("SELECT f FROM tb_feiras_adocao AS f " +
            "WHERE upper(f.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR cast(f.dataHora as string) LIKE concat('%', ?1, '%')")
    Page<FeiraAdocao> search(String value, Pageable page);
}