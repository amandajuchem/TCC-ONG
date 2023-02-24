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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Exame service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ExameService {

    private final ExameRepository repository;

    /**
     * Delete exame.
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

        throw new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND);
    }

    /**
     * Find all exames.
     *
     * @return the list
     */
    public Page<Exame> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Save exame.
     *
     * @param exame the exame
     * @return the exame
     */
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
     * Search exames.
     *
     * @param value     Nome ou categoria
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of exames
     */
    public Page<Exame> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
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