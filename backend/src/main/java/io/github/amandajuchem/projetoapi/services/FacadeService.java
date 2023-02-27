package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The type Facade service.
 */
@Service
@RequiredArgsConstructor
public class FacadeService {

    private final AdocaoService adocaoService;
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

    //////////////////////////////////////////////// ADOÇÃO ////////////////////////////////////////////////

    /**
     * Delete adoção.
     *
     * @param id the id
     */
    public void adocaoDelete(UUID id) {
        adocaoService.delete(id);
    }

    /**
     * Find all adoções.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of adoção
     */
    public Page<Adocao> adocaoFindAll(Integer page, Integer size, String sort, String direction) {
        return adocaoService.findAll(page, size, sort, direction);
    }

    /**
     * Find adoção by id.
     *
     * @param id the id
     * @return the adoção
     */
    public Adocao adocaoFindById(UUID id) {
        return adocaoService.findById(id);
    }

    /**
     * Save adoção.
     *
     * @param adocao the adocao
     * @return the adoção
     */
    public Adocao adocaoSave(Adocao adocao) {
        return adocaoService.save(adocao);
    }

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
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list
     */
    public Page<Agendamento> agendamentoFindAll(Integer page, Integer size, String sort, String direction) {
        return agendamentoService.findAll(page, size, sort, direction);
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

    /**
     * Search agendamentos.
     *
     * @param value     Data, nome do animal ou nome do veterinário
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of agendamentos
     */
    public Page<Agendamento> agendamentoSearch(String value, Integer page, Integer size, String sort, String direction) {
        return agendamentoService.search(value, page, size, sort, direction);
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
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list
     */
    public Page<Animal> animalFindAll(Integer page, Integer size, String sort, String direction) {
        return animalService.findAll(page, size, sort, direction);
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
     * Save animal.
     *
     * @param animal the animal
     * @return the animal
     */
    public Animal animalSave(Animal animal) {
        return animalService.save(animal);
    }

    /**
     * Search animais.
     *
     * @param value     Nome
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of animais
     */
    public Page<Animal> animalSearch(String value, Integer page, Integer size, String sort, String direction) {
        return animalService.search(value, page, size, sort, direction);
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
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list
     */
    public Page<Atendimento> atendimentoFindAll(Integer page, Integer size, String sort, String direction) {
        return atendimentoService.findAll(page, size, sort, direction);
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


    /**
     * Search atendimentos.
     *
     * @param value     Data, nome do animal ou nome do veterinário
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of atendimentos
     */
    public Page<Atendimento> atendimentoSearch(String value, Integer page, Integer size, String sort, String direction) {
        return atendimentoService.search(value, page, size, sort, direction);
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
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list
     */
    public Page<Exame> exameFindAll(Integer page, Integer size, String sort, String direction) {
        return exameService.findAll(page, size, sort, direction);
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

    /**
     * Search exames.
     *
     * @param value     Nome ou categoria
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the page
     */
    public Page<Exame> exameSearch(String value, Integer page, Integer size, String sort, String direction) {
        return exameService.search(value, page, size, sort, direction);
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
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of tutores
     */
    public Page<Tutor> tutorFindAll(Integer page, Integer size, String sort, String direction) {
        return tutorService.findAll(page, size, sort, direction);
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
     * Save tutor.
     *
     * @param tutor the tutor
     * @return the tutor
     */
    public Tutor tutorSave(Tutor tutor) {
        return tutorService.save(tutor);
    }

    /**
     * Search tutores.
     *
     * @param value     Nome, CPF ou RG
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of tutores.
     */
    public Page<Tutor> tutorSearch(String value, Integer page, Integer size, String sort, String direction) {
        return tutorService.search(value, page, size, sort, direction);
    }

    //////////////////////////////////////////////// USUÁRIO ////////////////////////////////////////////////

    /**
     * Find all usuarios.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of usuários
     */
    public Page<Usuario> usuarioFindAll(Integer page, Integer size, String sort, String direction) {
        return usuarioService.findAll(page, size, sort, direction);
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
     * Save usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public Usuario usuarioSave(Usuario usuario) {
        return usuarioService.save(usuario);
    }


    /**
     * Search usuários.
     *
     * @param value     Nome ou CPF
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the list of usuários
     */
    public Page<Usuario> usuarioSearch(String value, Integer page, Integer size, String sort, String direction) {
        return usuarioService.search(value, page, size, sort, direction);
    }
}