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

    private final AgendamentoService agendamentoService;
    private final AnimalService animalService;
    private final AtendimentoService atendimentoService;
    private final EnderecoService enderecoService;
    private final ExameService exameService;
    private final FichaMedicaService fichaMedicaService;
    private final ImagemService imagemService;
    private final TelefoneService telefoneService;
    private final TutorService tutorService;
    private final UsuarioService usuarioService;

    //////////////////////////////////////////////// AGENDAMENTO ////////////////////////////////////////////////

    /**
     * Delete agendamento.
     *
     * @param id the id
     */
    public void agendamentoDelete(UUID id) {
        agendamentoService.delete(id);
    }

    /**
     * Find all agendamentos.
     *
     * @return the list
     */
    public List<Agendamento> agendamentoFindAll() {
        return agendamentoService.findAll();
    }

    /**
     * Save agendamento.
     *
     * @param agendamento the agendamento
     * @return the agendamento
     */
    public Agendamento agendamentoSave(Agendamento agendamento) {
        return agendamentoService.save(agendamento);
    }

    //////////////////////////////////////////////// ANIMAL ////////////////////////////////////////////////

    /**
     * Delete animal.
     *
     * @param id the id
     */
    public void animalDelete(UUID id) {
        animalService.delete(id);
    }

    /**
     * Find all animais.
     *
     * @return the list
     */
    public List<Animal> animalFindAll() {
        return animalService.findAll();
    }

    /**
     * Find animal by id.
     *
     * @param id the id
     * @return the animal
     */
    public Animal animalFindById(UUID id) {
        return animalService.findById(id);
    }

    /**
     * Find animal by nome contains.
     *
     * @param nome the nome
     * @return the list
     */
    public List<Animal> animalFindByNomeContains(String nome) {
        return animalService.findByNomeContains(nome);
    }

    /**
     * Save animal.
     *
     * @param animal the animal
     * @return the animal
     */
    public Animal animalSave(Animal animal) {
        return animalService.save(animal);
    }

    //////////////////////////////////////////////// ATENDIMENTO ////////////////////////////////////////////////

    /**
     * Delete atendimento.
     *
     * @param id the id
     */
    public void atendimentoDelete(UUID id) {
        atendimentoService.delete(id);
    }

    /**
     * Find all atendimentos.
     *
     * @return the list
     */
    public List<Atendimento> atendimentoFindAll() {
        return atendimentoService.findAll();
    }

    /**
     * Find atendimento by id.
     *
     * @param id the id
     * @return the atendimento
     */
    public Atendimento atendimentoFindById(UUID id) {
        return atendimentoService.findById(id);
    }

    /**
     * Save atendimento.
     *
     * @param atendimento the atendimento
     * @return the atendimento
     */
    public Atendimento atendimentoSave(Atendimento atendimento) {
        return atendimentoService.save(atendimento);
    }

    //////////////////////////////////////////////// ENDEREÇO ////////////////////////////////////////////////

    /**
     * Delete endereco.
     *
     * @param id the id
     */
    public void enderecoDelete(UUID id) {
        enderecoService.delete(id);
    }

    /**
     * Save endereco.
     *
     * @param endereco the endereco
     * @return the endereco
     */
    public Endereco enderecoSave(Endereco endereco) {
        return enderecoService.save(endereco);
    }

    //////////////////////////////////////////////// EXAME ////////////////////////////////////////////////

    /**
     * Delete exame.
     *
     * @param id the id
     */
    public void exameDelete(UUID id) {
        exameService.delete(id);
    }

    /**
     * Find all exames.
     *
     * @return the list
     */
    public List<Exame> exameFindAll() {
        return exameService.findAll();
    }

    /**
     * Save exame.
     *
     * @param exame the exame
     * @return the exame
     */
    public Exame exameSave(Exame exame) {
        return exameService.save(exame);
    }

    //////////////////////////////////////////////// FICHA MÉDICA ////////////////////////////////////////////////

    /**
     * Delete ficha medica.
     *
     * @param id the id
     */
    public void fichaMedicaDelete(UUID id) {
        fichaMedicaService.delete(id);
    }

    /**
     * Save ficha medica.
     *
     * @param fichaMedica the ficha medica
     * @return the ficha medica
     */
    public FichaMedica fichaMedicaSave(FichaMedica fichaMedica) {
        return fichaMedicaService.save(fichaMedica);
    }

    //////////////////////////////////////////////// IMAGEM ////////////////////////////////////////////////

    /**
     * Delete imagem.
     *
     * @param id the id
     */
    public void imagemDelete(UUID id) {
        imagemService.delete(id);
    }

    /**
     * Save imagem.
     *
     * @param imagem the imagem
     * @return the imagem
     */
    public Imagem imagemSave(Imagem imagem) {
        return imagemService.save(imagem);
    }

    //////////////////////////////////////////////// TELEFONE ////////////////////////////////////////////////

    /**
     * Delete telefone.
     *
     * @param id the id
     */
    public void telefoneDelete(UUID id) {
        telefoneService.delete(id);
    }

    /**
     * Save telefone.
     *
     * @param telefone the telefone
     * @return the telefone
     */
    public Telefone telefoneSave(Telefone telefone) {
        return telefoneService.save(telefone);
    }

    //////////////////////////////////////////////// TUTOR ////////////////////////////////////////////////

    /**
     * Delete tutor.
     *
     * @param id the id
     */
    public void tutorDelete(UUID id) {
        tutorService.delete(id);
    }

    /**
     * Find all tutores.
     *
     * @return the list
     */
    public List<Tutor> tutorFindAll() {
        return tutorService.findAll();
    }

    /**
     * Find tutor by id.
     *
     * @param id the id
     * @return the tutor
     */
    public Tutor tutorFindById(UUID id) {
        return tutorService.findById(id);
    }


    /**
     * Find tutor by nome contains.
     *
     * @param nome the nome
     * @return the list
     */
    public List<Tutor> tutorFindByNomeContains(String nome) {
        return tutorService.findByNomeContains(nome);
    }

    /**
     * Save tutor.
     *
     * @param tutor the tutor
     * @return the tutor
     */
    public Tutor tutorSave(Tutor tutor) {
        return tutorService.save(tutor);
    }

    //////////////////////////////////////////////// USUÁRIO ////////////////////////////////////////////////

    /**
     * Find all usuarios.
     *
     * @return the list
     */
    public List<Usuario> usuarioFindAll() {
        return usuarioService.findAll();
    }

    /**
     * Find usuario by cpf.
     *
     * @param cpf the cpf
     * @return the usuario
     */
    public Usuario usuarioFindByCpf(String cpf) {
        return usuarioService.findByCpf(cpf);
    }

    /**
     * Find usuario by id.
     *
     * @param id the id
     * @return the usuario
     */
    public Usuario usuarioFindById(UUID id) {
        return usuarioService.findById(id);
    }

    /**
     * Find usuario by nome contains.
     *
     * @param nome the nome
     * @return the list
     */
    public List<Usuario> usuarioFindByNomeContains(String nome) {
        return usuarioService.findByNomeContains(nome);
    }

    /**
     * Save usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public Usuario usuarioSave(Usuario usuario) {
        return usuarioService.save(usuario);
    }
}