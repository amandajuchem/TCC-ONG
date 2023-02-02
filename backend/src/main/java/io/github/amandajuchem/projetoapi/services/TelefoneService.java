package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Telefone;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.TelefoneRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Telefone service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TelefoneService {

    private final TelefoneRepository repository;

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

        throw new ObjectNotFoundException(MessageUtils.TELEFONE_NOT_FOUND);
    }

    /**
     * Save telefone.
     *
     * @param telefone the telefone
     * @return the telefone
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Telefone save(Telefone telefone) {

        if (telefone ==  null) {
            throw new ValidationException(MessageUtils.TELEFONE_NULL);
        }

        if (validateTelefone(telefone)) {
            telefone = repository.save(telefone);
        }

        return telefone;
    }

    /**
     * Validate telefone.
     *
     * @param telefone the telefone
     * @return the boolean
     */
    private boolean validateTelefone(Telefone telefone) {

        return true;
    }
}