package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * Find by data hora and veterinario optional.
     *
     * @param dataHora    the data hora
     * @param veterinario the veterinario
     * @return the optional
     */
    Optional<Atendimento> findByDataHoraAndVeterinario(LocalDateTime dataHora, Usuario veterinario);
}