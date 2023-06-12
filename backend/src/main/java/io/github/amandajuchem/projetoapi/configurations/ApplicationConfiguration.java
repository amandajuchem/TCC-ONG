package io.github.amandajuchem.projetoapi.configurations;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * Application configuration class implementing CommandLineRunner interface.
 */
@Component
@RequiredArgsConstructor
public class ApplicationConfiguration implements CommandLineRunner {

    private final UsuarioService service;

    /**
     * Runs the application configuration.
     *
     * @param args the command-line arguments
     */
    @Override
    public void run(String... args) {
        checkDefaultUsuario();
        createFolders();
    }

    /**
     * Creates the necessary folders for the application.
     */
    private void createFolders() {

        var data = new File("data");

        if (!data.exists()) {
            data.mkdir();
        }

        var files = new File("data/files");

        if (!files.exists()) {
            files.mkdir();
        }
    }


    /**
     * Checks if a default Usuario exists, and if not, saves a default one.
     */
    private void checkDefaultUsuario() {

        final var usuario = new Usuario();

        try {
            var usuarioDTO = service.findByCpf("03129686550");
            BeanUtils.copyProperties(usuarioDTO, usuario);
            saveDefaultUsuario(usuario);
        } catch (Exception ex) {
            saveDefaultUsuario(usuario);
        }
    }

    /**
     * Saves a default Usuario with predefined values.
     *
     * @param usuario the Usuario object to be saved
     */
    private void saveDefaultUsuario(Usuario usuario) {

        usuario.setNome("AMANDA JUCHEM");
        usuario.setCpf("03129686550");
        usuario.setSenha("admin");
        usuario.setSetor(Setor.ADMINISTRACAO);
        usuario.setStatus(true);

        service.save(usuario);
    }
}