package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Exame;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.ExameRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Exame service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExameService {

    private final ExameRepository repository;

    /**
     * Delete exame.
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

        throw new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND);
    }

    /**
     * Find all exames.
     *
     * @return the list
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Exame> findAll() {
        return repository.findAll();
    }

    /**
     * Save exame.
     *
     * @param exame the exame
     * @return the exame
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Exame save(Exame exame) {

        if (exame == null) {
            throw new ValidationException(MessageUtils.EXAME_NULL);
        }

        if (validateExame(exame)) {
            exame = repository.save(exame);
        }

        return exame;
    }

    /**
     * Validate exame.
     *
     * @param exame the exame
     * @return the boolean
     */
    private boolean validateExame(Exame exame) {

        var exame_findByNome = repository.findByNomeIgnoreCase(exame.getNome()).orElse(null);

        if (exame_findByNome != null && !exame_findByNome.equals(exame)) {
            throw new ValidationException("Exame já cadastrado!");
        }

        return true;
    }
}