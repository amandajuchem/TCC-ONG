package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Exame;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.ExameRepository;
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
 * The type Exame service.
 */
@Service
@RequiredArgsConstructor
public class ExameService implements AbstractService<Exame> {

    private final ExameRepository repository;

    /**
     * Delete exame.
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

        throw new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND);
    }

    /**
     * Find all exame.
     *
     * @return the exame list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Exame> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find exame by id.
     *
     * @param id the id
     * @return the exame
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Exame findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND);
        });
    }

    /**
     * Save exame.
     *
     * @param exame the exame
     * @return the exame
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Exame save(Exame exame) {

        if (exame == null) {
            throw new ValidationException(MessageUtils.EXAME_NULL);
        }

        if (validate(exame)) {
            exame = repository.save(exame);
        }

        return exame;
    }

    /**
     * Search exame.
     *
     * @param value     nome ou categoria
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the exame list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Exame> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate exame.
     *
     * @param exame the exame
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Exame exame) {

        var exame_findByNome = repository.findByNomeIgnoreCase(exame.getNome()).orElse(null);

        if (exame_findByNome != null && !exame_findByNome.equals(exame)) {
            throw new ValidationException("Exame já cadastrado!");
        }

        return true;
    }
}