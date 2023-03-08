package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.FichaMedica;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.FichaMedicaRepository;
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
 * The type Ficha Medica service.
 */
@Service
@RequiredArgsConstructor
public class FichaMedicaService implements AbstractService<FichaMedica> {

    private final FichaMedicaRepository repository;

    /**
     * Delete ficha medica.
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

        throw new ObjectNotFoundException(MessageUtils.FICHA_MEDICA_NOT_FOUND);
    }

    /**
     * Find all ficha medica.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the ficha medica list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<FichaMedica> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find ficha medica by id.
     *
     * @param id the id
     * @return the ficha medica
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public FichaMedica findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.FICHA_MEDICA_NOT_FOUND);
        });
    }

    /**
     * Save ficha medica.
     *
     * @param fichaMedica the ficha medica
     * @return the ficha medica
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FichaMedica save(FichaMedica fichaMedica) {

        if (fichaMedica == null) {
            throw new ValidationException(MessageUtils.FICHA_MEDICA_NULL);
        }

        if (validate(fichaMedica)) {
            fichaMedica = repository.save(fichaMedica);
        }

        return fichaMedica;
    }

    /**
     * Validate ficha medica.
     *
     * @param fichaMedica the ficha medica
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(FichaMedica fichaMedica) {

        return true;
    }
}