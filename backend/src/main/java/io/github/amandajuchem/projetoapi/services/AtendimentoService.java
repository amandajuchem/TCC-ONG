package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AtendimentoRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Atendimento service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AtendimentoService {

    private final AtendimentoRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public Page<Atendimento> findAll(Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("veterinario")) {
            sort = "veterinario.nome";
        }

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }


    /**
     * Search atendimentos.
     *
     * @param value     Data, nome do animal ou nome do veterinário
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of atendimentos
     */
    public Page<Atendimento> search(String value, Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("veterinario")) {
            sort = "veterinario.nome";
        }

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find by id atendimento.
     *
     * @param id the id
     * @return the atendimento
     */
    public Atendimento findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND);
        });
    }

    /**
     * Save atendimento.
     *
     * @param atendimento the atendimento
     * @return the atendimento
     */
    public Atendimento save(Atendimento atendimento) {

        if (atendimento == null) {
            throw new ValidationException(MessageUtils.ATENDIMENTO_NULL);
        }

        if (validateAtendimento(atendimento)) {
            atendimento = repository.save(atendimento);
        }

        return atendimento;
    }

    /**
     * Validate atendimento.
     *
     * @param atendimento the atendimento
     * @return the boolean
     */
    private boolean validateAtendimento(Atendimento atendimento) {

        var atendimento_findByDataHoraAndVeterinario = repository.findByDataHoraAndVeterinario(atendimento.getDataHora(), atendimento.getVeterinario()).orElse(null);

        if (atendimento_findByDataHoraAndVeterinario != null && !atendimento_findByDataHoraAndVeterinario.equals(atendimento)) {
            throw new ValidationException("O veterinário já possui um atendimento realizado para esta data e hora!");
        }

        return true;
    }
}