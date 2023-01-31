package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Facade service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FacadeService {

    private final AnimalService animalService;
    private final AtendimentoService atendimentoService;
    private final EnderecoService enderecoService;
    private final FichaMedicaService fichaMedicaService;
    private final ImagemService imagemService;
    private final TutorService tutorService;
    private final UsuarioService usuarioService;

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
     * Animal find by nome contains list.
     *
     * @param nome the nome
     * @return the list
     */
    public List<Animal> animalFindByNomeContains(String nome) {
        return animalService.findByNomeContains(nome);
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

    //////////////////////////////////////////////// ATENDIMENTO ////////////////////////////////////////////////

    /**
     * Atendimento delete.
     *
     * @param id the id
     */
    public void atendimentoDelete(UUID id) {
        atendimentoService.delete(id);
    }

    /**
     * Atendimento find all list.
     *
     * @return the list
     */
    public List<Atendimento> atendimentoFindAll() {
        return atendimentoService.findAll();
    }

    /**
     * Atendimento find by id atendimento.
     *
     * @param id the id
     * @return the atendimento
     */
    public Atendimento atendimentoFindById(UUID id) {
        return atendimentoService.findById(id);
    }

    /**
     * Atendimento save atendimento.
     *
     * @param atendimento the atendimento
     * @return the atendimento
     */
    public Atendimento atendimentoSave(Atendimento atendimento) {
        return atendimentoService.save(atendimento);
    }

    //////////////////////////////////////////////// ENDEREÇO ////////////////////////////////////////////////

    /**
     * Endereco delete.
     *
     * @param id the id
     */
    public void enderecoDelete(UUID id) {
        enderecoService.delete(id);
    }

    /**
     * Endereco save endereco.
     *
     * @param endereco the endereco
     * @return the endereco
     */
    public Endereco enderecoSave(Endereco endereco) {
        return enderecoService.save(endereco);
    }

    //////////////////////////////////////////////// FICHA MÉDICA ////////////////////////////////////////////////

    /**
     * Ficha medica delete.
     *
     * @param id the id
     */
    public void fichaMedicaDelete(UUID id) {
        fichaMedicaService.delete(id);
    }

    /**
     * Ficha medica save ficha medica.
     *
     * @param fichaMedica the ficha medica
     * @return the ficha medica
     */
    public FichaMedica fichaMedicaSave(FichaMedica fichaMedica) {
        return fichaMedicaService.save(fichaMedica);
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
     * Usuario find by nome contains list.
     *
     * @param nome the nome
     * @return the list
     */
    public List<Usuario> usuarioFindByNomeContains(String nome) {
        return usuarioService.findByNomeContains(nome);
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