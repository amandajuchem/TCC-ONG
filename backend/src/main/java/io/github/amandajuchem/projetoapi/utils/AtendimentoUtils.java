package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * The type Atendimento utils.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AtendimentoUtils {

    private final FacadeService facade;

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        var atendimento = facade.atendimentoFindById(id);

        if (atendimento.getDocumentos() != null && atendimento.getDocumentos().size() > 0) {
            atendimento.getDocumentos().forEach(d -> facade.imagemDelete(d.getId()));
        }

        facade.atendimentoDelete(atendimento.getId());
    }

    /**
     * Save atendimento.
     *
     * @param atendimento        the atendimento
     * @param documentosToSave   the documentos to save
     * @param documentosToDelete the documentos to delete
     * @return the atendimento
     */
    public Atendimento save(Atendimento atendimento, List<MultipartFile> documentosToSave, List<UUID> documentosToDelete) {

        try {

            atendimento = facade.atendimentoSave(atendimento);

            if (documentosToSave != null && documentosToSave.size() > 0) {

                for (MultipartFile d : documentosToSave) {

                    try {

                        var file = FileUtils.save(d, FileUtils.IMAGES_DIRECTORY);

                        var imagem = Imagem.builder()
                                .nome(file.getName())
                                .atendimento(atendimento)
                                .build();

                        facade.imagemSave(imagem);
                    } catch (IOException ex) {
                        throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
                    }
                }
            }

            if (documentosToDelete != null && documentosToDelete.size() > 0) {
                documentosToDelete.forEach(facade::imagemDelete);
            }

            return atendimento;
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        }
    }
}