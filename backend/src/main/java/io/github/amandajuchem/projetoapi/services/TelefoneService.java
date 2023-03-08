package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Telefone;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.TelefoneRepository;
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
 * The type Telefone service.
 */
@Service
@RequiredArgsConstructor
public class TelefoneService implements AbstractService<Telefone> {

    private final TelefoneRepository repository;

    /**
     * Delete telefone.
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

        throw new ObjectNotFoundException(MessageUtils.TELEFONE_NOT_FOUND);
    }

    /**
     * Find all telefone.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the telefone list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Telefone> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find telefone by id
     *
     * @param id the id
     * @return the telefone
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Telefone findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.TELEFONE_NOT_FOUND);
        });
    }

    /**
     * Save telefone.
     *
     * @param telefone the telefone
     * @return the telefone
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Telefone save(Telefone telefone) {

        if (telefone == null) {
            throw new ValidationException(MessageUtils.TELEFONE_NULL);
        }

        if (validate(telefone)) {
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
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Telefone telefone) {

        var telefone_findByNumero = repository.findByNumero(telefone.getNumero()).orElse(null);

        if (telefone_findByNumero != null && !telefone_findByNumero.equals(telefone)) {
            throw new ValidationException("Telefone já cadastrado!");
        }

        return true;
    }
}