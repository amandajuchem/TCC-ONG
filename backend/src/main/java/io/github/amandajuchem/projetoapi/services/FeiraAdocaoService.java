package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.FeiraAdocaoDTO;
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
 * Service class that implements the AbstractService interface for managing adoption fair objects.
 */
@Service
@RequiredArgsConstructor
public class FeiraAdocaoService implements AbstractService<FeiraAdocao, FeiraAdocaoDTO> {

    private final FeiraAdocaoRepository repository;

    /**
     * Deletes an adoption fair by ID.
     *
     * @param id the ID of the adoption fair object to be deleted.
     * @throws ObjectNotFoundException if the adoption fair object with the given ID is not found.
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
     * Retrieves all adoption fairs.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested FeiraAdocaoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<FeiraAdocaoDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(FeiraAdocaoDTO::toDTO);
    }

    /**
     * Retrieves an adoption fair by ID.
     *
     * @param id the ID of the adoption fair object to be retrieved.
     * @return the FeiraAdocaoDTO representing the requested adoption fair object.
     * @throws ObjectNotFoundException if the adoption fair object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public FeiraAdocaoDTO findById(UUID id) {
        final var feiraAdocao = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.FEIRA_ADOCAO_NOT_FOUND));
        return FeiraAdocaoDTO.toDTO(feiraAdocao);
    }

    /**
     * Saves an adoption fair.
     *
     * @param feiraAdocao the adoption fair object to be saved.
     * @return the FeiraAdocaoDTO representing the saved adoption fair object.
     * @throws ValidationException if the adoption fair object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FeiraAdocaoDTO save(FeiraAdocao feiraAdocao) {

        if (feiraAdocao == null) {
            throw new ValidationException(MessageUtils.FEIRA_ADOCAO_NULL);
        }

        if (validate(feiraAdocao)) {
            feiraAdocao = repository.save(feiraAdocao);
        }

        return FeiraAdocaoDTO.toDTO(feiraAdocao);
    }

    /**
     * Search for adoption fairs by value.
     *
     * @param value     the value to search for (name or date/time) case-insensitive.
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested FeiraAdocaoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<FeiraAdocaoDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(FeiraAdocaoDTO::toDTO);
    }

    /**
     * Validates an adoption fair.
     *
     * @param feiraAdocao the adoption fair object to be validated.
     * @return true if the adoption fair object is valid.
     * @throws ValidationException if an adoption fair object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(FeiraAdocao feiraAdocao) {

        final var feiraAdocaoFindByNome = repository.findByNomeIgnoreCase(feiraAdocao.getNome());

        if (feiraAdocaoFindByNome.isPresent()) {

            if (!feiraAdocaoFindByNome.get().equals(feiraAdocao)) {
                throw new ValidationException("Feira de adoção já cadastrada!");
            }
        }

        return true;
    }
}