package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.FichaMedica;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.FichaMedicaRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The type Ficha medica service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FichaMedicaService {

    private final FichaMedicaRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
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
     * Save ficha medica.
     *
     * @param fichaMedica the ficha medica
     * @return the ficha medica
     */
    public FichaMedica save(FichaMedica fichaMedica) {

        if (fichaMedica == null) {
            throw new ValidationException(MessageUtils.FICHA_MEDICA_NULL);
        }

        if (validateFichaMedica(fichaMedica)) {
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
    private boolean validateFichaMedica(FichaMedica fichaMedica) {

        return true;
    }
}