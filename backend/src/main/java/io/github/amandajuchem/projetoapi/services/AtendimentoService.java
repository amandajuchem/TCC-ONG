package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.AtendimentoDTO;
import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AtendimentoRepository;
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
 * Service class that implements the AbstractService interface for managing treatment objects.
 */
@Service
@RequiredArgsConstructor
public class AtendimentoService implements AbstractService<Atendimento, AtendimentoDTO> {

    private final AtendimentoRepository repository;

    /**
     * Deletes a treatment by ID.
     *
     * @param id the ID of the treatment object to be deleted.
     * @throws ObjectNotFoundException if the treatment object with the given ID is not found.
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

        throw new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND);
    }

    /**
     * Retrieves all treatments.
     *
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a Page object containing the requested AtendimentoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<AtendimentoDTO> findAll(Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("veterinario")) {
            sort = "veterinario.nome";
        }

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AtendimentoDTO::toDTO);
    }

    /**
     * Retrieves a treatment by ID.
     *
     * @param id the ID of the treatment object to be retrieved.
     * @return the AtendimentoDTO representing the requested treatment object.
     * @throws ObjectNotFoundException if the treatment object with the given ID is not found.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AtendimentoDTO findById(UUID id) {
        final var atendimento = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND));
        return AtendimentoDTO.toDTO(atendimento);
    }

    /**
     * Saves a treatment.
     *
     * @param atendimento the treatment object to be saved.
     * @return the AtendimentoDTO representing the saved treatment object.
     * @throws ValidationException if the treatment object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AtendimentoDTO save(Atendimento atendimento) {

        if (atendimento == null) {
            throw new ValidationException(MessageUtils.ATENDIMENTO_NULL);
        }

        if (validate(atendimento)) {
            atendimento = repository.save(atendimento);
        }

        return AtendimentoDTO.toDTO(atendimento);
    }

    /**
     * Search for treatments by value.
     *
     * @param value     the value to search for (date/time, animal's name, or veterinarian's name) case-insensitive.
     * @param page      the page number for pagination.
     * @param size      the page size for pagination.
     * @param sort      the sorting field.
     * @param direction the sorting direction ("asc" for ascending, "desc" for descending).
     * @return a page object containing the requested AtendimentoDTO objects.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<AtendimentoDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        if (sort.equalsIgnoreCase("animal")) {
            sort = "animal.nome";
        }

        if (sort.equalsIgnoreCase("veterinario")) {
            sort = "veterinario.nome";
        }

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AtendimentoDTO::toDTO);
    }

    /**
     * Validates a treatment object.
     *
     * @param atendimento the treatment object to be validated.
     * @return true if the treatment object is valid.
     * @throws ValidationException if the treatment object is invalid.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Atendimento atendimento) {

        final var atendimentoFindByDataHoraAndVeterinario = repository.findByDataHoraAndVeterinario(atendimento.getDataHora(), atendimento.getVeterinario())
                .orElse(null);

        if (atendimentoFindByDataHoraAndVeterinario != null && !atendimentoFindByDataHoraAndVeterinario.equals(atendimento)) {
            throw new ValidationException("O veterinário já possui um atendimento realizado para esta data e hora!");
        }

        return true;
    }
}