package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.ObservacaoDTO;
import io.github.amandajuchem.projetoapi.entities.Observacao;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.ObservacaoRepository;
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
public class ObservacaoService implements AbstractService<Observacao, ObservacaoDTO> {

    private final ObservacaoRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {

        if (id != null) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.OBSERVACAO_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObservacaoDTO> findAll(Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ObservacaoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ObservacaoDTO findById(UUID id) {
        final var observacao = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.OBSERVACAO_NOT_FOUND));
        return ObservacaoDTO.toDTO(observacao);
    }

    @Override
    @Transactional
    public ObservacaoDTO save(Observacao observacao) {
        validate(observacao);
        observacao = repository.save(observacao);
        return ObservacaoDTO.toDTO(observacao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObservacaoDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(ObservacaoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(Observacao observacao) {

        if (observacao == null) {
            throw new ValidationException(MessageUtils.OBSERVACAO_NULL);
        }
    }

    private String getSortByField(String sort) {
        if (sort.equalsIgnoreCase("tutor")) {
            return "tutor.nome";
        }
        return sort;
    }
}
