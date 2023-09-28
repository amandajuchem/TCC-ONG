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

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

    /**
     * Search for treatments by value.
     *
     * @param value    The value to search for (date/time, animal's name, or veterinarian's name) case-insensitive.
     * @param pageable The pageable object specifying the page information.
     * @return A page of treatment entities matching the search criteria.
     */
    @Query("SELECT a FROM tb_atendimentos AS a " +
            "INNER JOIN a.animal AS an " +
            "INNER JOIN a.veterinario AS v " +
            "WHERE cast(a.dataHora as string) LIKE concat('%', ?1, '%') " +
            "OR upper(an.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(v.nome) LIKE upper(concat('%', ?1, '%'))")
    Page<Atendimento> search(String value, Pageable pageable);

    /**
     * Retrieves a treatment by date/time and veterinarian.
     *
     * @param dataHora    The date/time of the treatment.
     * @param veterinario The veterinarian associated with the treatment.
     * @return An Optional containing the treatment entity if found, or an empty Optional if not found.
     */
    Optional<Atendimento> findByDataHoraAndVeterinario(LocalDateTime dataHora, Usuario veterinario);
}