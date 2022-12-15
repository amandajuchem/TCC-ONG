package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Tutor repository.
 */
@Repository
public interface TutorRepository extends JpaRepository<Tutor, UUID> { }