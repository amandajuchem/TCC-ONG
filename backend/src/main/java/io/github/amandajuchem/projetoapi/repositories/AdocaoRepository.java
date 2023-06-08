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

    @Query("SELECT ad FROM tb_adocoes AS ad " +
            "INNER JOIN ad.animal AS an " +
            "INNER JOIN ad.tutor AS t " +
            "ON an.id = ?1 " +
            "OR t.id = ?1")
    Page<Adocao> search(String value, Pageable page);
}