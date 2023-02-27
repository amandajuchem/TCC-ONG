package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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

    private final FacadeService facade;

    /**
     * Delete adoção.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        var adocao = facade.adocaoFindById(id);

        if (adocao.getTermoResponsabilidade() != null && adocao.getTermoResponsabilidade().size() > 0) {
            adocao.getTermoResponsabilidade().forEach(termo -> facade.imagemDelete(termo.getId()));
        }

        facade.adocaoDelete(adocao.getId());
    }

    /**
     * Save adoção.
     *
     * @param adocao             the adocao
     * @param documentosToSave   the documentos to save
     * @param documentosToDelete the documentos to delete
     * @return the adocao
     */
    public Adocao save(Adocao adocao, List<MultipartFile> documentosToSave, List<UUID> documentosToDelete) {

        adocao = facade.adocaoSave(adocao);

        if (documentosToSave != null && documentosToSave.size() > 0) {

            for (MultipartFile documento : documentosToSave) {

                try {

                    var file = FileUtils.save(documento, FileUtils.IMAGES_DIRECTORY);

                    var imagem = Imagem.builder()
                            .nome(file.getName())
                            .adocao(adocao)
                            .build();

                    facade.imagemSave(imagem);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
                }
            }
        }

        if (documentosToDelete != null && documentosToDelete.size() > 0) {
            documentosToDelete.forEach(facade::imagemDelete);
        }

        return adocao;
    }
}