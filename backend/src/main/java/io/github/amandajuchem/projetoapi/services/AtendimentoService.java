package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.AtendimentoDTO;
import io.github.amandajuchem.projetoapi.entities.Atendimento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AtendimentoRepository;
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
public class AtendimentoService implements AbstractService<Atendimento, AtendimentoDTO> {

    private final AtendimentoRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {

        if (id != null) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoDTO> findAll(Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AtendimentoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public AtendimentoDTO findById(UUID id) {
        final var atendimento = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.ATENDIMENTO_NOT_FOUND));
        return AtendimentoDTO.toDTO(atendimento);
    }

    @Override
    @Transactional
    public AtendimentoDTO save(Atendimento atendimento) {
        validate(atendimento);
        atendimento = repository.save(atendimento);
        return AtendimentoDTO.toDTO(atendimento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AtendimentoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(Atendimento atendimento) {

        if (atendimento == null) {
            throw new ValidationException(MessageUtils.ATENDIMENTO_NULL);
        }

        validateDataHoraAndVeterinario(atendimento);
    }

    private String getSortByField(String sort) {

        return switch (sort) {
            case "animal" -> "animal.nome";
            case "veterinario" -> "veterinario.nome";
            default -> sort;
        };
    }

    private void validateDataHoraAndVeterinario(Atendimento atendimento) {
        repository.findByDataHoraAndVeterinario(atendimento.getDataHora(), atendimento.getVeterinario())
                .ifPresent(existingAtendimento -> {
                    if (!existingAtendimento.equals(atendimento)) {
                        throw new ValidationException("O veterinário já possui um atendimento realizado na data e hora informados!");
                    }
                });
    }
}