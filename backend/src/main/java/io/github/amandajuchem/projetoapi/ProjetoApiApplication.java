package io.github.amandajuchem.projetoapi;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * The type Projeto api application.
 */
@SpringBootApplication
@RequiredArgsConstructor
public class ProjetoApiApplication implements CommandLineRunner {

    private final UsuarioService service;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjetoApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        checkDefaultUsuario();
        createFolders();
    }

    private void createFolders() {

        var files = new File("files");

        if (!files.exists()) {
            files.mkdir();
        }
    }

    private void checkDefaultUsuario() {

        try {
            var usuario = service.findByCpf("03129686550");
            saveDefaultUsuario(usuario);
        } catch (Exception ex) {
            saveDefaultUsuario(new Usuario());
        }
    }

    private void saveDefaultUsuario(Usuario usuario) {

        usuario.setNome("AMANDA JUCHEM");
        usuario.setCpf("03129686550");
        usuario.setSenha("admin");
        usuario.setSetor(Setor.ADMINISTRACAO);
        usuario.setStatus(true);

        service.save(usuario);
    }
}