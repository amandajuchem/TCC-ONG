package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.TutorDTO;
import io.github.amandajuchem.projetoapi.entities.Tutor;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.TutorRepository;
import io.github.amandajuchem.projetoapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TutorService implements AbstractService<Tutor, TutorDTO> {

    private final TutorRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<TutorDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(TutorDTO::toDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public TutorDTO findById(UUID id) {
        final var tutor = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND));
        return TutorDTO.toDTO(tutor);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TutorDTO save(Tutor tutor) {

        if (tutor == null) {
            throw new ValidationException(MessageUtils.TUTOR_NULL);
        }

        if (validate(tutor)) {
            tutor = repository.save(tutor);
        }

        return TutorDTO.toDTO(tutor);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<TutorDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(TutorDTO::toDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Tutor tutor) {

        final var tutor_findByNome = repository.findByNomeIgnoreCase(tutor.getNome()).orElse(null);

        if (tutor_findByNome != null && !tutor_findByNome.equals(tutor)) {
            throw new ValidationException("Tutor já cadastrado");
        }

        final var tutor_findByCpf = repository.findByCpf(tutor.getCpf()).orElse(null);

        if (tutor_findByCpf != null && !tutor_findByCpf.equals(tutor)) {
            throw new ValidationException("CPF já cadastrado!");
        }

        if (tutor.getRg() != null && !tutor.getRg().isEmpty()) {

            final var tutor_findByRg = repository.findByRg(tutor.getRg()).orElse(null);

            if (tutor_findByRg != null && !tutor_findByRg.equals(tutor)) {
                throw new ValidationException("RG já cadastrado!");
            }
        }

        return true;
    }
}