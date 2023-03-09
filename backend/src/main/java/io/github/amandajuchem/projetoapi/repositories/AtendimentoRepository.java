package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Atendimento repository.
 */
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

    /**
     * Search atendimento.
     *
     * @param value    the data, nome do animal ou nome do veterinário
     * @param pageable the pageable
     * @return the atendimento list
     */
    @Query(value = "SELECT a FROM tb_atendimentos AS a INNER JOIN a.animal AS an INNER JOIN a.veterinario AS v " +
            "ON cast(a.dataHora as string) LIKE concat('%', ?1, '%') " +
            "OR upper(an.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(v.nome) LIKE upper(concat('%', ?1, '%'))")
    Page<Atendimento> search(String value, Pageable pageable);

    /**
     * Find atendimento by data hora and veterinário.
     *
     * @param dataHora    the data hora
     * @param veterinario the veterinário
     * @return the atendimento optional
     */
    Optional<Atendimento> findByDataHoraAndVeterinario(LocalDateTime dataHora, Usuario veterinario);
}