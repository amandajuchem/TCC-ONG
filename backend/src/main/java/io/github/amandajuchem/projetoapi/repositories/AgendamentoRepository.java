package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Agendamento;
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
 * The interface Agendamento repository.
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    /**
     * Search agendamentos.
     *
     * @param value    Data, nome do animal ou nome do veterinário
     * @param pageable the pageable
     * @return the list of agendamentos
     */
    @Query(value = "SELECT a FROM tb_agendamentos AS a INNER JOIN a.animal AS an INNER JOIN a.veterinario AS v " +
            "ON cast(a.dataHora as string) LIKE concat('%', ?1, '%') " +
            "OR upper(an.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(v.nome) LIKE upper(concat('%', ?1, '%'))")
    Page<Agendamento> search(String value, Pageable pageable);

    /**
     * Find agendamento by data hora and veterinario.
     *
     * @param dataHora    the data hora
     * @param veterinario the veterinario
     * @return the optional of agendamento
     */
    Optional<Agendamento> findByDataHoraAndVeterinario(LocalDateTime dataHora, Usuario veterinario);
}