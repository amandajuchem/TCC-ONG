package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.AdocaoDTO;
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
 * Service class that implements the AbstractService interface for managing adoption objects.
 */
@Service
@RequiredArgsConstructor
public class AdocaoService implements AbstractService<Adocao, AdocaoDTO> {

    private final AdocaoRepository repository;

    /**
     * Deletes an Adocao object by its ID.
     *
     * @param id the ID of the adoption object to be deleted.
     * @throws ObjectNotFoundException if the adoption object with the given ID is not found.
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
     * Retrieves all adoptions.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested AdocaoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<AdocaoDTO> findAll(Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("tutor")) {
            sort = "tutor.nome";
        }

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AdocaoDTO::toDTO);
    }

    /**
     * Retrieves an adoption by ID.
     *
     * @param id the ID of the adoption object to be retrieved.
     * @return the AdocaoDTO representing the requested adoption object.
     * @throws ObjectNotFoundException if the adoption object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AdocaoDTO findById(UUID id) {
        final var adocao = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.ADOCAO_NOT_FOUND));
        return AdocaoDTO.toDTO(adocao);
    }

    /**
     * Saves an adoption.
     *
     * @param adocao the adoption object to be saved.
     * @return the AdocaoDTO representing the saved adoption object.
     * @throws ValidationException if the adoption object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AdocaoDTO save(Adocao adocao) {

        if (adocao == null) {
            throw new ValidationException(MessageUtils.ADOCAO_NULL);
        }

        if (validate(adocao)) {
            adocao = repository.save(adocao);
        }

        return AdocaoDTO.toDTO(adocao);
    }

    /**
     * Search for adoptions by value.
     *
     * @param value     the value to search for (animal's ID or tutor's ID).
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested AdocaoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<AdocaoDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("tutor")) {
            sort = "tutor.nome";
        }

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AdocaoDTO::toDTO);
    }

    /**
     * Validates an adoption object.
     *
     * @param adocao the adoption object to validate.
     * @return true if the adoption object is valid.
     * @throws ValidationException if the adoption object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Adocao adocao) {
        return true;
    }
}