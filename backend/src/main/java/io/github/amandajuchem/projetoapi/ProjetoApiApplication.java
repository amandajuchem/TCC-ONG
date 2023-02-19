package io.github.amandajuchem.projetoapi;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;
import io.github.amandajuchem.projetoapi.services.FacadeService;
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

    private final FacadeService facade;

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
            var usuario = facade.usuarioFindByCpf("07905836584");
            saveDefaultUsuario(usuario);
        } catch (Exception ex) {
            saveDefaultUsuario(new Usuario());
        }
    }

    private void saveDefaultUsuario(Usuario usuario) {

        usuario.setNome("Edson Isaac");
        usuario.setCpf("07905836584");
        usuario.setSenha("$2a$12$xi5jwI8SFzkS.LYJ73OAHOJb3mEhOeFJk2Gj3pzPKcQBM2SGVUr2a");
        usuario.setSetor(Setor.ADMINISTRACAO);
        usuario.setStatus(true);

        facade.usuarioSave(usuario);
    }
}