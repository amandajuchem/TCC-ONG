package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.FeiraAdocaoRepository;
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
 * The type Feira adocao service.
 */
@Service
@RequiredArgsConstructor
public class FeiraAdocaoService implements AbstractService<FeiraAdocao> {

    private final FeiraAdocaoRepository repository;

    /**
     * Delete.
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

        throw new ObjectNotFoundException(MessageUtils.FEIRA_ADOCAO_NOT_FOUND);
    }

    /**
     * Find all.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<FeiraAdocao> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public FeiraAdocao findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.FEIRA_ADOCAO_NOT_FOUND);
        });
    }

    /**
     * Save.
     *
     * @param feiraAdocao the object
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FeiraAdocao save(FeiraAdocao feiraAdocao) {

        if (feiraAdocao == null) {
            throw new ValidationException(MessageUtils.FEIRA_ADOCAO_NULL);
        }

        if (validate(feiraAdocao)) {
            feiraAdocao = repository.save(feiraAdocao);
        }

        return feiraAdocao;
    }

    /**
     * Search.
     *
     * @param value     the value
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<FeiraAdocao> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate.
     *
     * @param feiraAdocao the object
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(FeiraAdocao feiraAdocao) {

        var feiraAdocao_findByNome = repository.findByNomeIgnoreCase(feiraAdocao.getNome());

        if (feiraAdocao_findByNome.isPresent()) {

            if (!feiraAdocao_findByNome.get().equals(feiraAdocao)) {
                throw new ValidationException("Feira de adoção já cadastrada!");
            }
        }

        return true;
    }
}