package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AtendimentoRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<Atendimento> findAll() {
        return repository.findAll();
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