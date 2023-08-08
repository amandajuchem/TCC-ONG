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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TutorService implements AbstractService<Tutor, TutorDTO> {

    private final TutorRepository repository;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public Page<TutorDTO> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(TutorDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public TutorDTO findById(UUID id) {
        final var tutor = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.TUTOR_NOT_FOUND));
        return TutorDTO.toDTO(tutor);
    }

    @Override
    @Transactional
    public TutorDTO save(Tutor tutor) {
        validate(tutor);
        tutor = repository.save(tutor);
        return TutorDTO.toDTO(tutor);
    }

    @Transactional(readOnly = true)
    public Page<TutorDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(TutorDTO::toDTO);
    }

    @Transactional(readOnly = true)
    public void validate(Tutor tutor) {

        if (tutor == null) {
            throw new ValidationException(MessageUtils.TUTOR_NULL);
        }

        validateRg(tutor.getRg(), tutor);
    }

    private void validateRg(String rg, Tutor tutor) {
        if (rg != null && !rg.isEmpty()) {
            repository.findByRg(rg)
                    .ifPresent(existingTutor -> {
                        if (!existingTutor.equals(tutor)) {
                            throw new ValidationException("RG já cadastrado!");
                        }
                    });
        }
    }
}