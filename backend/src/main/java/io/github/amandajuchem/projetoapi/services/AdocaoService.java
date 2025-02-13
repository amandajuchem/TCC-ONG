package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.AdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.Adocao;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AdocaoRepository;
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
public class AdocaoService implements AbstractService<Adocao, AdocaoDTO> {

    private final AdocaoRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {

        if (id != null) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.ADOCAO_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdocaoDTO> findAll(Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AdocaoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public AdocaoDTO findById(UUID id) {
        final var adocao = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.ADOCAO_NOT_FOUND));
        return AdocaoDTO.toDTO(adocao);
    }

    @Override
    @Transactional
    public AdocaoDTO save(Adocao adocao) {
        validate(adocao);
        adocao = repository.save(adocao);
        return AdocaoDTO.toDTO(adocao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdocaoDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AdocaoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(Adocao adocao) {

        if (adocao == null) {
            throw new ValidationException(MessageUtils.ADOCAO_NULL);
        }
    }

    private String getSortByField(String sort) {
        return sort.equalsIgnoreCase("tutor") ? "tutor.nome" : sort;
    }
}
