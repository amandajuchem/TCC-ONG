package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Atendimento repository.
 */
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {
}