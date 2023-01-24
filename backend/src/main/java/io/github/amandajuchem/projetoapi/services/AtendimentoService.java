package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AtendimentoRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Atendimento service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AtendimentoService {

    private final AtendimentoRepository repository;

    /**
     * Delete.
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

        throw new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Atendimento> findAll() {
        return repository.findAll();
    }

    /**
     * Find by id atendimento.
     *
     * @param id the id
     * @return the atendimento
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
    @Transactional(propagation = Propagation.REQUIRED)
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


        return true;
    }
}