package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Imagem repository.
 */
@Repository
public interface ImagemRepository extends JpaRepository<Imagem, UUID> {


    /**
     * Find imagem by nome.
     *
     * @param nome the nome
     * @return the optional
     */
    Optional<Imagem> findByNome(String nome);
}