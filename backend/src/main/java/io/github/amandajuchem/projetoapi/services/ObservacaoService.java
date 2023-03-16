package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Observacao;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.ObservacaoRepository;
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
 * The type Observacao service.
 */
@Service
@RequiredArgsConstructor
public class ObservacaoService implements AbstractService<Observacao> {

    private final ObservacaoRepository repository;

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

        throw new ObjectNotFoundException(MessageUtils.OBSERVACAO_NOT_FOUND);
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
    public Page<Observacao> findAll(Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("tutor")) {
            sort = "tutor.nome";
        }

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find all.
     *
     * @param tutorId
     * @param page
     * @param size
     * @param sort
     * @param direction
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Observacao> findAll(UUID tutorId, Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("tutor")) {
            sort = "tutor.nome";
        }

        return repository.findAll(tutorId, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Observacao findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.OBSERVACAO_NOT_FOUND);
        });
    }

    /**
     * Save.
     *
     * @param observacao the object
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Observacao save(Observacao observacao) {

        if (observacao == null) {
            throw new ValidationException(MessageUtils.ADOCAO_NULL);
        }

        if (validate(observacao)) {
            observacao = repository.save(observacao);
        }

        return observacao;
    }

    /**
     * Validate.
     *
     * @param observacao the object
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Observacao observacao) {

        return true;
    }
}