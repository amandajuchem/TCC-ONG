package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.ImagemRepository;
import io.github.amandajuchem.projetoapi.utils.FileUtils;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Imagem service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ImagemService {

    private final ImagemRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
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
     * Save imagem.
     *
     * @param imagem the imagem
     * @return the imagem
     */
    public Imagem save(Imagem imagem) {

        if (imagem == null) {
            throw new ValidationException(MessageUtils.IMAGEM_NULL);
        }

        return repository.save(imagem);
    }
}