package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Endereco;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.EnderecoRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Endereco service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository repository;

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

        throw new ObjectNotFoundException(MessageUtils.ENDERECO_NOT_FOUND);
    }

    /**
     * Save endereco.
     *
     * @param endereco the endereco
     * @return the endereco
     */
    public Endereco save(Endereco endereco) {

        if (endereco == null) {
            throw new ValidationException(MessageUtils.ENDERECO_NULL);
        }

        if (validateEndereco(endereco)) {
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
    private boolean validateEndereco(Endereco endereco) {

        return true;
    }
}