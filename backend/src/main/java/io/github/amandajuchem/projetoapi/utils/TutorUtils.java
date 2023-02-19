package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Telefone;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Tutor utils.
 */
@Component
@RequiredArgsConstructor
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

        if (tutor.getTelefones() != null) {
            tutor.getTelefones().forEach(t -> facade.telefoneDelete(t.getId()));
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

                var telefones = tutor.getTelefones();
                var endereco = tutor.getEndereco();

                tutor.setTelefones(null);
                tutor.setEndereco(null);

                tutor = facade.tutorSave(tutor);

                for (Telefone telefone : telefones) {
                    telefone.setTutor(tutor);
                }

                endereco.setTutor(tutor);

                telefones = telefones.stream().map(t -> facade.telefoneSave(t)).collect(Collectors.toSet());
                endereco = facade.enderecoSave(endereco);

                tutor.setTelefones(telefones);
                tutor.setEndereco(endereco);
            }

            else {

                var tutor_old = facade.tutorFindById(tutor.getId());
                var telefones = tutor.getTelefones();
                var endereco = tutor.getEndereco();

                // Remove os telefones que foram excluidos
                for (Telefone telefone : tutor_old.getTelefones()) {

                    if (!telefones.contains(telefone)) {
                        facade.telefoneDelete(telefone.getId());
                        tutor.getTelefones().remove(telefone);
                    }
                }

                telefones = telefones.stream().map(t -> facade.telefoneSave(t)).collect(Collectors.toSet());
                endereco = facade.enderecoSave(endereco);

                tutor = facade.tutorSave(tutor);

                tutor.setTelefones(telefones);
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