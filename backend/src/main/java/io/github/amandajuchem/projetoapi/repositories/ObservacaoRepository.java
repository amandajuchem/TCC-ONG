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

    @Query("SELECT o FROM tb_observacoes AS o INNER JOIN o.tutor AS t ON t.id = ?1")
    Page<Observacao> search(String value, Pageable page);
}