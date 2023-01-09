package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Facade service.
 */
@Service
public class FacadeService {

    private final AnimalService animalService;
    private final ImagemService imagemService;
    private final TutorService tutorService;
    private final UsuarioService usuarioService;

    /**
     * Instantiates a new Facade service.
     *
     * @param animalService  the animal service
     * @param imagemService  the imagem service
     * @param tutorService   the tutor service
     * @param usuarioService the usuario service
     */
    @Autowired
    public FacadeService(
            AnimalService animalService,
            ImagemService imagemService,
            TutorService tutorService,
            UsuarioService usuarioService
    ) {
        this.animalService = animalService;
        this.imagemService = imagemService;
        this.tutorService = tutorService;
        this.usuarioService = usuarioService;
    }

    //////////////////////////////////////////////// ANIMAL ////////////////////////////////////////////////

    /**
     * Animal delete.
     *
     * @param id the id
     */
    public void animalDelete(UUID id) {
        animalService.delete(id);
    }

    /**
     * Animal find all list.
     *
     * @return the list
     */
    public List<Animal> animalFindAll() {
        return animalService.findAll();
    }

    /**
     * Animal save animal.
     *
     * @param animal the animal
     * @return the animal
     */
    public Animal animalSave(Animal animal) {
        return animalService.save(animal);
    }

    //////////////////////////////////////////////// IMAGEM ////////////////////////////////////////////////

    /**
     * Imagem delete.
     *
     * @param id the id
     */
    public void imagemDelete(UUID id) {
        imagemService.delete(id);
    }

    /**
     * Imagem save imagem.
     *
     * @param imagem the imagem
     * @return the imagem
     */
    public Imagem imagemSave(Imagem imagem) {
        return imagemService.save(imagem);
    }

    //////////////////////////////////////////////// TUTOR ////////////////////////////////////////////////


    //////////////////////////////////////////////// USUÁRIO ////////////////////////////////////////////////

    /**
     * Usuario find all list.
     *
     * @return the list
     */
    public List<Usuario> usuarioFindAll() {
        return usuarioService.findAll();
    }

    /**
     * Usuario find by cpf usuario.
     *
     * @param cpf the cpf
     * @return the usuario
     */
    public Usuario usuarioFindByCpf(String cpf) {
        return usuarioService.findByCpf(cpf);
    }

    /**
     * Usuario save usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public Usuario usuarioSave(Usuario usuario) {
        return usuarioService.save(usuario);
    }
}