package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.AgendamentoDTO;
import io.github.amandajuchem.projetoapi.entities.Agendamento;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.AgendamentoRepository;
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
public class AgendamentoService implements AbstractService<Agendamento, AgendamentoDTO> {

    private final AgendamentoRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {
        if (id != null) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.AGENDAMENTO_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoDTO> findAll(Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AgendamentoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoDTO findById(UUID id) {
        final var agendamento = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.AGENDAMENTO_NOT_FOUND));
        return AgendamentoDTO.toDTO(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoDTO save(Agendamento agendamento) {
        validate(agendamento);
        agendamento = repository.save(agendamento);
        return AgendamentoDTO.toDTO(agendamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        sort = getSortByField(sort);
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(AgendamentoDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(Agendamento agendamento) {

        if (agendamento == null) {
            throw new ValidationException(MessageUtils.AGENDAMENTO_NULL);
        }

        validateDataHoraAndVeterinario(agendamento);
    }

    private String getSortByField(String sort) {

        return switch (sort) {
            case "animal" -> "animal.nome";
            case "veterinario" -> "veterinario.nome";
            default -> sort;
        };
    }

    private void validateDataHoraAndVeterinario(Agendamento agendamento) {
        repository.findByDataHoraAndVeterinario(agendamento.getDataHora(), agendamento.getVeterinario())
                .ifPresent(existingAgendamento -> {
                    if (!existingAgendamento.equals(agendamento)) {
                        throw new ValidationException("O veterinário já possui um agendamento marcado para esta data e hora!");
                    }
                });
    }
}