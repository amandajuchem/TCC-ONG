package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AgendamentoRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Agendamento service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AgendamentoService {

    private final AgendamentoRepository repository;

    /**
     * Delete agendamento.
     *
     * @param id the id
     */
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
     * Find all agendamentos.
     *
     * @return the list
     */
    public List<Agendamento> findAll() {
        return repository.findAll();
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

        return true;
    }
}