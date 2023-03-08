package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.services.AdocaoService;
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
 * The type Adocao utils.
 */
@Component
@RequiredArgsConstructor
public class AdocaoUtils {

    private final AdocaoService adocaoService;
    private final ImagemService imagemService;

    /**
     * Delete adoção.
     *
     * @param id the id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        var adocao = adocaoService.findById(id);

        if (adocao.getTermoResponsabilidade() != null && adocao.getTermoResponsabilidade().size() > 0) {
            adocao.getTermoResponsabilidade().forEach(termo -> imagemService.delete(termo.getId()));
        }

        adocaoService.delete(adocao.getId());
    }

    /**
     * Save adocao.
     *
     * @param adocao             the adocao
     * @param documentosToSave   the documentos to save
     * @param documentosToDelete the documentos to delete
     * @return the adocao
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Adocao save(Adocao adocao, List<MultipartFile> documentosToSave, List<UUID> documentosToDelete) {

        adocao = adocaoService.save(adocao);

        if (documentosToSave != null && documentosToSave.size() > 0) {

            for (MultipartFile documento : documentosToSave) {

                try {

                    var file = FileUtils.save(documento, FileUtils.IMAGES_DIRECTORY);

                    var imagem = Imagem.builder()
                            .nome(file.getName())
                            .adocao(adocao)
                            .build();

                    imagemService.save(imagem);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
                }
            }
        }

        if (documentosToDelete != null && documentosToDelete.size() > 0) {
            documentosToDelete.forEach(imagemService::delete);
        }

        return adocao;
    }
}