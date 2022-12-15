package io.github.amandajuchem.projetoapi;

import io.github.amandajuchem.projetoapi.entities.Usuario;
import io.github.amandajuchem.projetoapi.enums.Setor;
import io.github.amandajuchem.projetoapi.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Projeto api application.
 */
@SpringBootApplication
public class ProjetoApiApplication implements CommandLineRunner {

	private final FacadeService facade;

	/**
	 * Instantiates a new Projeto api application.
	 *
	 * @param facade the facade
	 */
	@Autowired
	public ProjetoApiApplication(FacadeService facade) {
		this.facade = facade;
	}

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
	}

	private void checkDefaultUsuario() {

		try {
			var usuario = facade.usuarioFindByCpf("99999999999");
			saveDefaultUsuario(usuario);
		} catch (Exception ex) {
			saveDefaultUsuario(new Usuario());
		}
	}

	private void saveDefaultUsuario(Usuario usuario) {

		usuario.setNome("Administrador");
		usuario.setCpf("99999999999");
		usuario.setSenha("$2a$12$xi5jwI8SFzkS.LYJ73OAHOJb3mEhOeFJk2Gj3pzPKcQBM2SGVUr2a");
		usuario.setSetor(Setor.ADMINISTRACAO);
		usuario.setStatus(true);

		facade.usuarioSave(usuario);
	}
}