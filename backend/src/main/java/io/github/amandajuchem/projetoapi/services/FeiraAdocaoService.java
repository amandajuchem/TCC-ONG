package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.FeiraAdocaoDTO;
import io.github.amandajuchem.projetoapi.entities.FeiraAdocao;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.FeiraAdocaoRepository;
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
public class FeiraAdocaoService implements AbstractService<FeiraAdocao, FeiraAdocaoDTO> {

    private final FeiraAdocaoRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.FEIRA_ADOCAO_NOT_FOUND);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<FeiraAdocaoDTO> findAll(Integer page, Integer size, String sort, String direction) {

        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(FeiraAdocaoDTO::toDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public FeiraAdocaoDTO findById(UUID id) {
        final var feiraAdocao = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.FEIRA_ADOCAO_NOT_FOUND));
        return FeiraAdocaoDTO.toDTO(feiraAdocao);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FeiraAdocaoDTO save(FeiraAdocao feiraAdocao) {

        if (feiraAdocao == null) {
            throw new ValidationException(MessageUtils.FEIRA_ADOCAO_NULL);
        }

        if (validate(feiraAdocao)) {
            feiraAdocao = repository.save(feiraAdocao);
        }

        return FeiraAdocaoDTO.toDTO(feiraAdocao);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<FeiraAdocaoDTO> search(String value, Integer page, Integer size, String sort, String direction) {

        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(FeiraAdocaoDTO::toDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(FeiraAdocao feiraAdocao) {

        final var feiraAdocao_findByNome = repository.findByNomeIgnoreCase(feiraAdocao.getNome());

        if (feiraAdocao_findByNome.isPresent()) {

            if (!feiraAdocao_findByNome.get().equals(feiraAdocao)) {
                throw new ValidationException("Feira de adoção já cadastrada!");
            }
        }

        return true;
    }
}