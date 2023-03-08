package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.AtendimentoService;
import io.github.amandajuchem.projetoapi.services.ImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * The type Atendimento utils.
 */
@Component
@RequiredArgsConstructor
public class AtendimentoUtils {

    private final AtendimentoService atendimentoService;
    private final ImagemService imagemService;

    /**
     * Delete.
     *
     * @param id the id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        var atendimento = atendimentoService.findById(id);

        if (atendimento.getDocumentos() != null && atendimento.getDocumentos().size() > 0) {
            atendimento.getDocumentos().forEach(documento -> imagemService.delete(documento.getId()));
        }

        atendimentoService.delete(atendimento.getId());
    }

    /**
     * Save atendimento.
     *
     * @param atendimento        the atendimento
     * @param documentosToSave   the documentos to save
     * @param documentosToDelete the documentos to delete
     * @return the atendimento
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Atendimento save(Atendimento atendimento, List<MultipartFile> documentosToSave, List<UUID> documentosToDelete) {

        try {

            atendimento = atendimentoService.save(atendimento);

            if (documentosToSave != null && documentosToSave.size() > 0) {

                for (MultipartFile d : documentosToSave) {

                    try {

                        var file = FileUtils.save(d, FileUtils.IMAGES_DIRECTORY);

                        var imagem = Imagem.builder()
                                .nome(file.getName())
                                .atendimento(atendimento)
                                .build();

                        imagemService.save(imagem);
                    } catch (IOException ex) {
                        throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
                    }
                }
            }

            if (documentosToDelete != null && documentosToDelete.size() > 0) {
                documentosToDelete.forEach(imagemService::delete);
            }

            return atendimento;
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        }
    }
}