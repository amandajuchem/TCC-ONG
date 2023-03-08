package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Telefone repository.
 */
@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {


    /**
     * Find telefone by numero.
     *
     * @param numero the numero
     * @return the optional
     */
    Optional<Telefone> findByNumero(String numero);
}