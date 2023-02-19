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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Agendamento service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;

    /**
     * Delete agendamento.
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

        throw new ObjectNotFoundException(MessageUtils.AGENDAMENTO_NOT_FOUND);
    }

    /**
     * Find all agendamentos.
     *
     * @return the list
     */
    public Page<Agendamento> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find agendamentos by data hora or animal or veterinario.
     *
     * @param value     the value
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of agendamentos
     */
    public Page<Agendamento> findByDataHoraOrAnimalOrVeterinario(String value, Integer page, Integer size, String sort, String direction) {
        return repository.findByDataHoraOrAnimalOrVeterinario(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Save agendamento.
     *
     * @param agendamento the agendamento
     * @return the agendamento
     */
    public Agendamento save(Agendamento agendamento) {

        if (agendamento == null) {
            throw new ValidationException(MessageUtils.AGENDAMENTO_NULL);
        }

        if (validateAgendamento(agendamento)) {
            agendamento = repository.save(agendamento);
        }

        return agendamento;
    }

    /**
     * Validate agendamento.
     *
     * @param agendamento the agendamento
     * @return the boolean
     */
    private boolean validateAgendamento(Agendamento agendamento) {

        var agendamento_findByDataHoraAndVeterinario = repository.findByDataHoraAndVeterinario(agendamento.getDataHora(), agendamento.getVeterinario()).orElse(null);

        if (agendamento_findByDataHoraAndVeterinario != null && !agendamento_findByDataHoraAndVeterinario.equals(agendamento)) {
            throw new ValidationException("O veterinário já possui um agendamento marcado para esta data e hora!");
        }

        return true;
    }
}