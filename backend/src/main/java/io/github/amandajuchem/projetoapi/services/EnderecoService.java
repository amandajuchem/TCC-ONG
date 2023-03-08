package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Endereco;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.EnderecoRepository;
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
 * The type Endereco service.
 */
@Service
@RequiredArgsConstructor
public class EnderecoService implements AbstractService<Endereco> {

    private final EnderecoRepository repository;

    /**
     * Delete endereco.
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

        throw new ObjectNotFoundException(MessageUtils.ENDERECO_NOT_FOUND);
    }

    /**
     * Find all endereco.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the endereco list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Endereco> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find endereco by id.
     *
     * @param id the id
     * @return the endereco
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Endereco findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.ENDERECO_NOT_FOUND);
        });
    }

    /**
     * Save endereco.
     *
     * @param endereco the endereco
     * @return the endereco
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Endereco save(Endereco endereco) {

        if (endereco == null) {
            throw new ValidationException(MessageUtils.ENDERECO_NULL);
        }

        if (validate(endereco)) {
            endereco = repository.save(endereco);
        }

        return endereco;
    }

    /**
     * Validate endereco.
     *
     * @param endereco the endereco
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Endereco endereco) {

        return true;
    }
}