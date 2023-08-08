package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.ExameDTO;
import io.github.amandajuchem.projetoapi.entities.Exame;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.ExameRepository;
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
public class ExameService implements AbstractService<Exame, ExameDTO> {

    private final ExameRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {

        if (id != null) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExameDTO> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ExameDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ExameDTO findById(UUID id) {
        final var exame = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.EXAME_NOT_FOUND));
        return ExameDTO.toDTO(exame);
    }

    @Override
    @Transactional
    public ExameDTO save(Exame exame) {
        validate(exame);
        exame = repository.save(exame);
        return ExameDTO.toDTO(exame);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExameDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ExameDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(Exame exame) {

        if (exame == null) {
            throw new ValidationException(MessageUtils.EXAME_NULL);
        }
    }
}