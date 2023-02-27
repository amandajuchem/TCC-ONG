package io.github.amandajuchem.projetoapi.services;

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

import java.util.UUID;

/**
 * The type Adocao service.
 */
@Service
@RequiredArgsConstructor
public class AdocaoService {

    private final AdocaoRepository repository;

    /**
     * Delete adoção.
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

        throw new ObjectNotFoundException(MessageUtils.ADOCAO_NOT_FOUND);
    }

    /**
     * Find all adoções.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the page
     */
    public Page<Adocao> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find adoção by id.
     *
     * @param id the id
     * @return the adoção
     */
    public Adocao findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.ADOCAO_NOT_FOUND);
        });
    }

    /**
     * Save adoção.
     *
     * @param adocao the adocao
     * @return the adocao
     */
    public Adocao save(Adocao adocao) {

        if (adocao == null) {
            throw new ValidationException(MessageUtils.ADOCAO_NULL);
        }

        if (validateAdocao(adocao)) {
            adocao = repository.save(adocao);
        }

        return adocao;
    }

    /**
     * Validate adoção.
     *
     * @param adocao the adocao
     * @return the boolean
     */
    private boolean validateAdocao(Adocao adocao) {

        return true;
    }
}