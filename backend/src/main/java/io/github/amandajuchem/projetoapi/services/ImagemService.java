package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.ImagemRepository;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
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
 * The type Imagem service.
 */
@Service
@RequiredArgsConstructor
public class ImagemService implements AbstractService<Imagem> {

    private final ImagemRepository repository;

    /**
     * Delete imagem.
     *
     * @param id the id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        if (repository.existsById(id)) {

            var imagem = repository.findById(id).get();

            repository.deleteById(id);
            FileUtils.delete(imagem.getNome(), FileUtils.IMAGES_DIRECTORY);
            return;
        }

        throw new ObjectNotFoundException(MessageUtils.IMAGEM_NOT_FOUND);
    }

    /**
     * Find all imagem.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the imagem list.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Imagem> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find imagem by id.
     *
     * @param id the id
     * @return the imagem
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Imagem findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.IMAGEM_NOT_FOUND);
        });
    }

    /**
     * Save imagem.
     *
     * @param imagem the imagem
     * @return the imagem
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Imagem save(Imagem imagem) {

        if (imagem == null) {
            throw new ValidationException(MessageUtils.IMAGEM_NULL);
        }

        if (validate(imagem)) {
            imagem = repository.save(imagem);
        }

        return imagem;
    }

    /**
     * Validate imagem.
     *
     * @param imagem the imagem
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Imagem imagem) {

        var imagem_findByNome = repository.findByNome(imagem.getNome()).orElse(null);

        if (imagem_findByNome != null && !imagem_findByNome.equals(imagem)) {
            throw new ValidationException("Imagem já cadastrada!");
        }

        return true;
    }
}