package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * The type Usuario utils.
 */
@Component
@RequiredArgsConstructor(onConstructor_= {@Autowired})
public class UsuarioUtils {

    private final BCryptPasswordEncoder encoder;
    private final FacadeService facade;

    /**
     * Encode password usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public Usuario encodePassword(Usuario usuario) {

        if (usuario.getId() != null) {

            var usuario_findByCpf = facade.usuarioFindByCpf(usuario.getCpf());

            if (!usuario.getSenha().equals(usuario_findByCpf.getSenha())) {
                usuario.setSenha(encoder.encode(usuario.getSenha()));
            }
        } else {
            usuario.setSenha(encoder.encode(usuario.getSenha()));
        }

        return usuario;
    }

    /**
     * Save usuario.
     *
     * @param usuario    the usuario
     * @param novaFoto   the nova foto
     * @param antigaFoto the antiga foto
     * @return the usuario
     */
    public Usuario save(Usuario usuario, MultipartFile novaFoto, UUID antigaFoto) {

        try {

            usuario = facade.usuarioSave(encodePassword(usuario));

            if (novaFoto != null) {

                var file = FileUtils.save(novaFoto, FileUtils.IMAGES_DIRECTORY);

                var imagem = Imagem.builder()
                        .nome(file.getName())
                        .build();

                usuario.setFoto(imagem);
            }

            if (antigaFoto != null) {
                facade.imagemDelete(antigaFoto);
            }

            return facade.usuarioSave(usuario);
        } catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        } catch (IOException ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}