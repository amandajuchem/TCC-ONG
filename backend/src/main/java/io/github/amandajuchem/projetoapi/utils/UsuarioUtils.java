package io.github.amandajuchem.projetoapi.utils;

import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.exceptions.OperationFailureException;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * The type Usuario utils.
 */
@Component
public class UsuarioUtils {

    private final BCryptPasswordEncoder encoder;
    private final FacadeService facade;

    /**
     * Instantiates a new Usuario utils.
     *
     * @param encoder the encoder
     * @param facade  the facade
     */
    @Autowired
    public UsuarioUtils(BCryptPasswordEncoder encoder, FacadeService facade) {
        this.encoder = encoder;
        this.facade = facade;
    }

    /**
     * Encode password usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public Usuario encodePassword(Usuario usuario) {

        if (usuario.getId() != null) {

            var usuario_findByCPF = facade.usuarioFindByCpf(usuario.getCpf());

            if (!usuario.getSenha().equals(usuario_findByCPF.getSenha())) {
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
                        .usuario(usuario)
                        .build();

                imagem = facade.imagemSave(imagem);
                usuario.setFoto(imagem);
            }

            if (antigaFoto != null) {
                facade.imagemDelete(antigaFoto);
            }

            return facade.usuarioSave(usuario);
        } catch (Exception ex) {
            throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
        }
    }
}