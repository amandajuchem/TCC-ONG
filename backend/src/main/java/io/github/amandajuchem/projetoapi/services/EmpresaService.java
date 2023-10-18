package io.github.amandajuchem.projetoapi.services;

import io.github.amandajuchem.projetoapi.dtos.EmpresaDTO;
import io.github.amandajuchem.projetoapi.entities.Empresa;
import io.github.amandajuchem.projetoapi.exceptions.ObjectNotFoundException;
import io.github.amandajuchem.projetoapi.exceptions.ValidationException;
import io.github.amandajuchem.projetoapi.repositories.EmpresaRepository;
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
public class EmpresaService implements AbstractService<Empresa, EmpresaDTO> {

    private final EmpresaRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {

        if (id != null) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.EMPRESA_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(EmpresaDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpresaDTO findById(UUID id) {
        final var empresa = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.EMPRESA_NOT_FOUND));
        return EmpresaDTO.toDTO(empresa);
    }

    @Override
    @Transactional
    public EmpresaDTO save(Empresa empresa) {
        validate(empresa);
        empresa = repository.save(empresa);
        return EmpresaDTO.toDTO(empresa);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(EmpresaDTO::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(Empresa empresa) {

        if (empresa == null) {
            throw new ValidationException(MessageUtils.EMPRESA_NULL);
        }

        repository.findByNomeIgnoreCase(empresa.getNome())
                .ifPresent(existingEmpresa -> {
                    if (!existingEmpresa.equals(empresa)) {
                        throw new ValidationException("Empresa já cadastrada!");
                    }
                });
    }
}
