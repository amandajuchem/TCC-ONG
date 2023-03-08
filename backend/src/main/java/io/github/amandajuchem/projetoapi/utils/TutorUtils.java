package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Telefone;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.EnderecoService;
import io.github.amandajuchem.projetoapi.services.ImagemService;
import io.github.amandajuchem.projetoapi.services.TelefoneService;
import io.github.amandajuchem.projetoapi.services.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    private final EnderecoService enderecoService;
    private final ImagemService imagemService;
    private final TelefoneService telefoneService;
    private final TutorService tutorService;

    /**
     * Delete tutor.
     *
     * @param id the id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        var tutor = tutorService.findById(id);

        if (tutor.getFoto() != null) {
            imagemService.delete(tutor.getFoto().getId());
        }

        if (tutor.getTelefones() != null) {
            tutor.getTelefones().forEach(t -> telefoneService.delete(t.getId()));
        }

        if (tutor.getEndereco() != null) {
            enderecoService.delete(tutor.getEndereco().getId());
        }

        tutorService.delete(tutor.getId());
    }

    /**
     * Save tutor.
     *
     * @param tutor      the tutor
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the tutor
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Tutor save(Tutor tutor, MultipartFile novaFoto, UUID antigaFoto) {

        try {

            if (tutor.getId() == null) {

                var telefones = tutor.getTelefones();
                var endereco = tutor.getEndereco();

                tutor.setTelefones(null);
                tutor.setEndereco(null);

                tutor = tutorService.save(tutor);

                for (Telefone telefone : telefones) {
                    telefone.setTutor(tutor);
                }

                endereco.setTutor(tutor);

                telefones = telefones.stream().map(t -> telefoneService.save(t)).collect(Collectors.toSet());
                endereco = enderecoService.save(endereco);

                tutor.setTelefones(telefones);
                tutor.setEndereco(endereco);
            }

            else {

                var tutor_old = tutorService.findById(tutor.getId());
                var telefones = tutor.getTelefones();
                var endereco = tutor.getEndereco();

                // Remove os telefones que foram excluidos
                for (Telefone telefone : tutor_old.getTelefones()) {

                    if (!telefones.contains(telefone)) {
                        telefoneService.delete(telefone.getId());
                    }
                }

                telefones = telefones.stream().map(t -> telefoneService.save(t)).collect(Collectors.toSet());
                endereco = enderecoService.save(endereco);

                tutor = tutorService.save(tutor);

                tutor.setTelefones(telefones);
                tutor.setEndereco(endereco);
            }

            if (novaFoto != null) {

                var file = FileUtils.save(novaFoto, FileUtils.IMAGES_DIRECTORY);

                var imagem = Imagem.builder()
                        .nome(file.getName())
                        .tutor(tutor)
                        .build();

                imagem = imagemService.save(imagem);
                tutor.setFoto(imagem);
            }

            if (antigaFoto != null) {
                imagemService.delete(antigaFoto);
            }

            return tutor;
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (IOException ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}