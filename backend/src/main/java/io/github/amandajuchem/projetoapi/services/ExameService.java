package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.ExameDTO;
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
 * Service class that implements the AbstractService interface for managing exam objects.
 */
@Service
@RequiredArgsConstructor
public class ExameService implements AbstractService<Exame, ExameDTO> {

    private final ExameRepository repository;

    /**
     * Deletes an exam by ID.
     *
     * @param id the ID of the exam object to be deleted.
     * @throws ObjectNotFoundException if the exam object with the given ID is not found.
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
     * Retrieves all exams.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested ExameDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<ExameDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ExameDTO::toDTO);
    }

    /**
     * Retrieves an exam by ID.
     *
     * @param id the ID of the exam object to be retrieved.
     * @return the ExameDTO representing the requested exam object.
     * @throws ObjectNotFoundException if the exam object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ExameDTO findById(UUID id) {
        final var exame = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND));
        return ExameDTO.toDTO(exame);
    }

    /**
     * Saves an exam.
     *
     * @param exame the exam object to be saved.
     * @return the ExameDTO representing the saved exam object.
     * @throws ValidationException if the exam object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ExameDTO save(Exame exame) {

        if (exame == null) {
            throw new ValidationException(MessageUtils.EXAME_NULL);
        }

        if (validate(exame)) {
            exame = repository.save(exame);
        }

        return ExameDTO.toDTO(exame);
    }

    /**
     * Search for exams by value.
     *
     * @param value     the value to search for (name or category) case-insensitive.
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested ExameDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<ExameDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ExameDTO::toDTO);
    }

    /**
     * Validates an exam.
     *
     * @param exame the exam object to be validated.
     * @return true if the exam object is valid.
     * @throws ValidationException if the exam object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Exame exame) {

        final var exameFindByNome = repository.findByNomeIgnoreCase(exame.getNome()).orElse(null);

        if (exameFindByNome != null && !exameFindByNome.equals(exame)) {
            throw new ValidationException("Exame já cadastrado!");
        }

        return true;
    }
}