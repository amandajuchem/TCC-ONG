package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AgendamentoRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Agendamento service.
 */
@Service
@RequiredArgsConstructor
public class AgendamentoService implements AbstractService<Agendamento> {

    private final AgendamentoRepository repository;

    /**
     * Delete agendamento.
     *
     * @param id the id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.AGENDAMENTO_NOT_FOUND);
    }

    /**
     * Find all agendamento.
     *
     * @return the agendamento list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Agendamento> findAll(Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("veterinario")) {
            sort = "veterinario.nome";
        }

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find agendamento by id.
     *
     * @param id the id
     * @return the agendamento
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Agendamento findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.AGENDAMENTO_NOT_FOUND);
        });
    }

    /**
     * Save agendamento.
     *
     * @param agendamento the agendamento
     * @return the agendamento
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Agendamento save(Agendamento agendamento) {

        if (agendamento == null) {
            throw new ValidationException(MessageUtils.AGENDAMENTO_NULL);
        }

        if (validate(agendamento)) {
            agendamento = repository.save(agendamento);
        }

        return agendamento;
    }

    /**
     * Search agendamento.
     *
     * @param value     the data, nome do animal ou nome do veterinário
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the agendamento list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Agendamento> search(String value, Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("veterinario")) {
            sort = "veterinario.nome";
        }

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate agendamento.
     *
     * @param agendamento the agendamento
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Agendamento agendamento) {

        var agendamento_findByDataHoraAndVeterinario = repository.findByDataHoraAndVeterinario(agendamento.getDataHora(), agendamento.getVeterinario()).orElse(null);

        if (agendamento_findByDataHoraAndVeterinario != null && !agendamento_findByDataHoraAndVeterinario.equals(agendamento)) {
            throw new ValidationException("O veterinário já possui um agendamento marcado para esta data e hora!");
        }

        return true;
    }
}