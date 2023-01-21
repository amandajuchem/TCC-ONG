package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.TutorRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Tutor service.
 */
@Service
@RequiredArgsConstructor(onConstructor_= {@Autowired})
public class TutorService {

    private final TutorRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Tutor> findAll() {
        return repository.findAll();
    }

    /**
     * Find by id tutor.
     *
     * @param id the id
     * @return the tutor
     */
    public Tutor findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND);
        });
    }


    /**
     * Find by nome like list.
     *
     * @param nome the nome
     * @return the list
     */
    public List<Tutor> findByNomeContains(String nome) {
        return repository.findByNomeContainsIgnoreCase(nome);
    }

    /**
     * Save tutor.
     *
     * @param tutor the tutor
     * @return the tutor
     */
    public Tutor save(Tutor tutor) {

        if (tutor == null) {
            throw new ValidationException(MessageUtils.TUTOR_NULL);
        }

        if (validateTutor(tutor)) {
            tutor = repository.save(tutor);
        }

        return tutor;
    }

    /**
     * Validate tutor.
     *
     * @param tutor the tutor
     * @return the boolean
     */
    private boolean validateTutor(Tutor tutor) {

        var tutor_findByNome = repository.findByNome(tutor.getNome()).orElse(null);

        if (tutor_findByNome != null && !tutor_findByNome.equals(tutor)) {
            throw new ValidationException("Tutor já cadastrado");
        }

        var tutor_findByCpf = repository.findByCpf(tutor.getCpf()).orElse(null);;

        if (tutor_findByCpf != null && !tutor_findByCpf.equals(tutor)) {
            throw new ValidationException("CPF já cadastrado!");
        }

        if (tutor.getRg() != null && !tutor.getRg().isEmpty()) {

            var tutor_findByRg = repository.findByRg(tutor.getRg()).orElse(null);

            if (tutor_findByRg != null && !tutor_findByRg.equals(tutor)) {
                throw new ValidationException("RG já cadastrado!");
            }
        }

        return true;
    }
}