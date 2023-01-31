package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * The type Tutor utils.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TutorUtils {

    private final FacadeService facade;

    /**
     * Delete tutor.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        var tutor = facade.tutorFindById(id);

        if (tutor.getFoto() != null) {
            facade.imagemDelete(tutor.getFoto().getId());
        }

        if (tutor.getEndereco() != null) {
            facade.enderecoDelete(tutor.getEndereco().getId());
        }

        facade.tutorDelete(tutor.getId());
    }

    /**
     * Save tutor.
     *
     * @param tutor      the tutor
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the tutor
     */
    public Tutor save(Tutor tutor, MultipartFile novaFoto, UUID antigaFoto) {

        try {

            if (tutor.getId() == null) {

                var endereco = tutor.getEndereco();

                tutor.setEndereco(null);
                tutor = facade.tutorSave(tutor);
                endereco.setTutor(tutor);
                endereco = facade.enderecoSave(endereco);
                tutor.setEndereco(endereco);
            }

            else {

                var endereco = tutor.getEndereco();

                endereco = facade.enderecoSave(endereco);
                tutor = facade.tutorSave(tutor);
                tutor.setEndereco(endereco);
            }

            if (novaFoto != null) {

                var file = FileUtils.save(novaFoto, FileUtils.IMAGES_DIRECTORY);

                var imagem = Imagem.builder()
                        .nome(file.getName())
                        .tutor(tutor)
                        .build();

                imagem = facade.imagemSave(imagem);
                tutor.setFoto(imagem);
            }

            if (antigaFoto != null) {
                facade.imagemDelete(antigaFoto);
            }

            return tutor;
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (IOException ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}