package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.FichaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Ficha medica repository.
 */
@Repository
public interface FichaMedicaRepository extends JpaRepository<FichaMedica, UUID> {
}