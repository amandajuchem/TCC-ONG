package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AdocaoRepository;
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
 * The type Adocao service.
 */
@Service
@RequiredArgsConstructor
public class AdocaoService implements AbstractService<Adocao> {

    private final AdocaoRepository repository;

    /**
     * Delete adocao.
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

        throw new ObjectNotFoundException(MessageUtils.ADOCAO_NOT_FOUND);
    }

    /**
     * Find all adocao.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the adocao list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Adocao> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find adocao by id.
     *
     * @param id the id
     * @return the adocao
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Adocao findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.ADOCAO_NOT_FOUND);
        });
    }

    /**
     * Save adocao.
     *
     * @param adocao the adocao
     * @return the adocao
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Adocao save(Adocao adocao) {

        if (adocao == null) {
            throw new ValidationException(MessageUtils.ADOCAO_NULL);
        }

        if (validate(adocao)) {
            adocao = repository.save(adocao);
        }

        return adocao;
    }

    /**
     * Validate adocao.
     *
     * @param adocao the adocao
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Adocao adocao) {

        return true;
    }
}