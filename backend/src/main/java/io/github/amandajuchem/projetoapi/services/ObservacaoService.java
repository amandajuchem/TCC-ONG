package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.ObservacaoDTO;
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
 * Service class that implements the AbstractService interface for managing observation objects.
 */
@Service
@RequiredArgsConstructor
public class ObservacaoService implements AbstractService<Observacao, ObservacaoDTO> {

    private final ObservacaoRepository repository;

    /**
     * Deletes an observation by ID.
     *
     * @param id the ID of the observation object to be deleted.
     * @throws ObjectNotFoundException if the observation object with the given ID is not found.
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
     * Retrieves all observations.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested ObservacaoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<ObservacaoDTO> findAll(Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("tutor")) {
            sort = "tutor.nome";
        }

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ObservacaoDTO::toDTO);
    }

    /**
     * Retrieves an observation by ID.
     *
     * @param id the ID of the observation object to be retrieved.
     * @return the ObservacaoDTO representing the requested observation object.
     * @throws ObjectNotFoundException if the observation object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ObservacaoDTO findById(UUID id) {
        final var observacao = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.OBSERVACAO_NOT_FOUND));
        return ObservacaoDTO.toDTO(observacao);
    }

    /**
     * Saves an observation.
     *
     * @param observacao the observation object to be saved.
     * @return the ObservacaoDTO representing the saved observation object.
     * @throws ValidationException if the observation object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ObservacaoDTO save(Observacao observacao) {

        if (observacao == null) {
            throw new ValidationException(MessageUtils.ADOCAO_NULL);
        }

        if (validate(observacao)) {
            observacao = repository.save(observacao);
        }

        return ObservacaoDTO.toDTO(observacao);
    }

    /**
     * Search for observations by value.
     *
     * @param value     the value to search for (tutor's ID).
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested ObservacaoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<ObservacaoDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("tutor")) {
            sort = "tutor.nome";
        }

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ObservacaoDTO::toDTO);
    }

    /**
     * Validates an observation.
     *
     * @param observacao the observation object to be validated.
     * @return true if the observation object is valid.
     * @throws ValidationException if the observation object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Observacao observacao) {
        return true;
    }
}