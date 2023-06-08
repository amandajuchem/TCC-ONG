package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID> {

    Optional<Tutor> findByCpf(String cpf);

    Optional<Tutor> findByNomeIgnoreCase(String nome);

    Optional<Tutor> findByRg(String rg);

    @Query(value = "SELECT t FROM tb_tutores AS t " +
            "WHERE upper(t.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR t.cpf LIKE concat('%', ?1, '%') " +
            "OR t.rg LIKE concat('%', ?1, '%') ")
    Page<Tutor> search(String value, Pageable page);
}