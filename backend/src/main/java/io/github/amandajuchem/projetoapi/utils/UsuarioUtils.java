package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.ImagemService;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * The type Usuario utils.
 */
@Component
@RequiredArgsConstructor
public class UsuarioUtils {

    private final ImagemService imagemService;
    private final UsuarioService usuarioService;

    /**
     * Save usuario.
     *
     * @param usuario    the usuario
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the usuario
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario save(Usuario usuario, MultipartFile novaFoto, UUID antigaFoto) {

        try {

            usuario = usuarioService.save(usuario);

            if (novaFoto != null) {

                var file = FileUtils.save(novaFoto, FileUtils.IMAGES_DIRECTORY);

                var imagem = Imagem.builder()
                        .nome(file.getName())
                        .usuario(usuario)
                        .build();

                imagem = imagemService.save(imagem);
                usuario.setFoto(imagem);
            }

            if (antigaFoto != null) {
                imagemService.delete(antigaFoto);
            }

            return usuario;
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (IOException ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}