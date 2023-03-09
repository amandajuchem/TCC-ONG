package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.TutorRepository;
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
 * The type Tutor service.
 */
@Service
@RequiredArgsConstructor
public class TutorService implements AbstractService<Tutor> {

    private final TutorRepository repository;

    /**
     * Delete tutor.
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

        throw new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND);
    }

    /**
     * Find all tutor.
     *
     * @return the tutor list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Tutor> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find tutor by id.
     *
     * @param id the id
     * @return the tutor
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Tutor findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND);
        });
    }

    /**
     * Save tutor.
     *
     * @param tutor the tutor
     * @return the tutor
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tutor save(Tutor tutor) {

        if (tutor == null) {
            throw new ValidationException(MessageUtils.TUTOR_NULL);
        }

        if (validate(tutor)) {
            tutor = repository.save(tutor);
        }

        return tutor;
    }

    /**
     * Search tutor.
     *
     * @param value     the nome, CPF ou RG
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the tutor list.
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Tutor> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate tutor.
     *
     * @param tutor the tutor
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Tutor tutor) {

        var tutor_findByNome = repository.findByNomeIgnoreCase(tutor.getNome()).orElse(null);

        if (tutor_findByNome != null && !tutor_findByNome.equals(tutor)) {
            throw new ValidationException("Tutor já cadastrado");
        }

        var tutor_findByCpf = repository.findByCpf(tutor.getCpf()).orElse(null);

        if (tutor_findByCpf != null && !tutor_findByCpf.equals(tutor)) {
            throw new ValidationException("CPF já cadastrado!");
        }

        if (tutor.getRg() != null && !tutor.getRg().isEmpty()) {

            var tutor_findByRg = repository.findByRg(tutor.getRg()).orElse(null);

            if (tutor_findByRg != null && !tutor_findByRg.equals(tutor)) {
                throw new ValidationException("RG já cadastrado!");
            }
        }

        return true;
    }
}