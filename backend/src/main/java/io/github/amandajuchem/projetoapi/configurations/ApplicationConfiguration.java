package io.github.amandajuchem.projetoapi.configurations;

import io.github.amandajuchem.projetoapi.entities.Empresa;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;
import io.github.amandajuchem.projetoapi.services.EmpresaService;
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

    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;

    /**
     * Runs the application configuration.
     *
     * @param args the command-line arguments
     */
    @Override
    public void run(String... args) {
        createFolders();
        checkDefaultUsuario();
        checkDefaultEmpresa();
    }

    /**
     * Creates the necessary folders for the application.
     */
    private void createFolders() {

        final var data = new File("data");

        if (!data.exists()) {
            data.mkdir();
        }

        final var files = new File("data/files");

        if (!files.exists()) {
            files.mkdir();
        }
    }

    /**
     * Checks if a default Empresa exists, and if not, saves a default one.
     */
    private void checkDefaultEmpresa() {

        final var empresasCount = empresaService.findAll(0, 10, "nome", "asc").getTotalElements();
        
        if (empresasCount == 0) {
            saveDefaultEmpresa();
        }
    }

    /**
     * Checks if a default Usuario exists, and if not, saves a default one.
     */
    private void checkDefaultUsuario() {

        final var usuario = new Usuario();

        try {
            final var usuarioDTO = usuarioService.findByCpf("03129686550");
            BeanUtils.copyProperties(usuarioDTO, usuario);
            saveDefaultUsuario(usuario);
        } catch (Exception ex) {
            saveDefaultUsuario(usuario);
        }
    }

    /**
     * Saves a default Empresa with predefined values.
     */
    private void saveDefaultEmpresa() {

        final var empresa = new Empresa();

        empresa.setNome("NOME DA EMPRESA");
        empresa.setCnpj("00000000000000");

        empresaService.save(empresa);
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

        usuarioService.save(usuario);
    }
}