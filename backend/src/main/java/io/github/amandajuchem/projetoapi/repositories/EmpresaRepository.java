package io.github.amandajuchem.projetoapi.repositories;

import io.github.amandajuchem.projetoapi.entities.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

    /**
     * Retrieves an empresa by name case-insensitive.
     *
     * @param nome The name of the empresa.
     * @return An Optional containing the empresa entity if found, or an empty Optional if not found.
     */
    Optional<Empresa> findByNomeIgnoreCase(String nome);

    /**
     * Search for empresas by value.
     *
     * @param value The value to search for (name or cnpj) case-insensitive.
     * @param page  The pageable object specifying the page information.
     * @return A page of empresa entities matching the search criteria.
     */
    @Query("SELECT e FROM tb_empresas AS e " +
            "WHERE upper(e.nome) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(e.cnpj) LIKE upper(concat('%', ?1, '%'))")
    Page<Empresa> search(String value, Pageable page);
}