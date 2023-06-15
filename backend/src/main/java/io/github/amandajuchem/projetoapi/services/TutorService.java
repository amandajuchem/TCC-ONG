package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.TutorDTO;
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
 * Service class that implements the AbstractService interface for managing tutor objects.
 */
@Service
@RequiredArgsConstructor
public class TutorService implements AbstractService<Tutor, TutorDTO> {

    private final TutorRepository repository;

    /**
     * Deletes a tutor by ID.
     *
     * @param id the ID of the tutor object to be deleted.
     * @throws ObjectNotFoundException if the tutor object with the given ID is not found.
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
     * Retrieves all tutors.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested TutorDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<TutorDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(TutorDTO::toDTO);
    }

    /**
     * Retrieves a tutor by ID.
     *
     * @param id the ID of the tutor object to be retrieved.
     * @return the TutorDTO representing the requested tutor object.
     * @throws ObjectNotFoundException if the tutor object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public TutorDTO findById(UUID id) {
        final var tutor = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND));
        return TutorDTO.toDTO(tutor);
    }

    /**
     * Saves a tutor.
     *
     * @param tutor the tutor object to be saved.
     * @return the TutorDTO representing the saved tutor object.
     * @throws ValidationException if the tutor object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TutorDTO save(Tutor tutor) {

        if (tutor == null) {
            throw new ValidationException(MessageUtils.TUTOR_NULL);
        }

        if (validate(tutor)) {
            tutor = repository.save(tutor);
        }

        return TutorDTO.toDTO(tutor);
    }

    /**
     * Search for tutors by value.
     *
     * @param value     the value to search for (name, CPF, or RG) case-insensitive.
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested TutorDTO objects.
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<TutorDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(TutorDTO::toDTO);
    }

    /**
     * Validates a tutor.
     *
     * @param tutor the tutor object to be validated.
     * @return true if the tutor object is valid.
     * @throws ValidationException if the tutor object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Tutor tutor) {

        final var tutorFindByNome = repository.findByNomeIgnoreCase(tutor.getNome()).orElse(null);

        if (tutorFindByNome != null && !tutorFindByNome.equals(tutor)) {
            throw new ValidationException("Tutor já cadastrado");
        }

        final var tutorFindByCpf = repository.findByCpf(tutor.getCpf()).orElse(null);

        if (tutorFindByCpf != null && !tutorFindByCpf.equals(tutor)) {
            throw new ValidationException("CPF já cadastrado!");
        }

        if (tutor.getRg() != null && !tutor.getRg().isEmpty()) {

            final var tutorFindByRg = repository.findByRg(tutor.getRg()).orElse(null);

            if (tutorFindByRg != null && !tutorFindByRg.equals(tutor)) {
                throw new ValidationException("RG já cadastrado!");
            }
        }

        return true;
    }
}