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

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    /**
     * Search for schedules by value.
     *
     * @param value    The value to search for (date/time, animal's name, or veterinarian's name) case-insensitive.
     * @param pageable The pageable object specifying the page information.
     * @return A page of scheduling entities matching the search criteria.
     */
    @Query(value = "SELECT ag FROM tb_agendamentos AS ag INNER JOIN ag.animal AS an INNER JOIN ag.veterinario AS v " +
            "ON cast(ag.dataHora as string) LIKE concat('%', ?1, '%') " +
            "OR upper(an.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(v.nome) LIKE upper(concat('%', ?1, '%'))")
    Page<Agendamento> search(String value, Pageable pageable);

    /**
     * Retrieves a scheduling by date/time and veterinarian.
     *
     * @param dataHora    The date/time of the scheduling.
     * @param veterinario The veterinarian associated with the scheduling.
     * @return An Optional containing the scheduling entity if found, or an empty Optional if not found.
     */
    Optional<Agendamento> findByDataHoraAndVeterinario(LocalDateTime dataHora, Usuario veterinario);
}
