package io.github.amandajuchem.projetoapi.configurations;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;
import io.github.amandajuchem.projetoapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class ApplicationConfiguration implements CommandLineRunner {

    private final UsuarioService service;

    @Override
    public void run(String... args) {
        checkDefaultUsuario();
        createFolders();
    }

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