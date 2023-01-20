package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Animal;
import io.github.amandajuchem.projetoapi.entities.Imagem;
import io.github.amandajuchem.projetoapi.entities.Tutor;
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
     * Animal find by id animal.
     *
     * @param id the id
     * @return the animal
     */
    public Animal animalFindById(UUID id) {
        return animalService.findById(id);
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

    /**
     * Tutor delete.
     *
     * @param id the id
     */
    public void tutorDelete(UUID id) {
        tutorService.delete(id);
    }

    /**
     * Tutor find all list.
     *
     * @return the list
     */
    public List<Tutor> tutorFindAll() {
        return tutorService.findAll();
    }

    /**
     * Tutor find by id tutor.
     *
     * @param id the id
     * @return the tutor
     */
    public Tutor tutorFindById(UUID id) {
        return tutorService.findById(id);
    }


    /**
     * Tutor find by nome contains list.
     *
     * @param nome the nome
     * @return the list
     */
    public List<Tutor> tutorFindByNomeContains(String nome) {
        return tutorService.findByNomeContains(nome);
    }

    /**
     * Tutor save tutor.
     *
     * @param tutor the tutor
     * @return the tutor
     */
    public Tutor tutorSave(Tutor tutor) {
        return tutorService.save(tutor);
    }

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
     * Usuario find by id usuario.
     *
     * @param id the id
     * @return the usuario
     */
    public Usuario usuarioFindById(UUID id) {
        return usuarioService.findById(id);
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