package io.github.amandajuchem.projetoapi.services;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AbstractService<T, DTO> {

    void delete(UUID id);

    Page<DTO> findAll(Integer page, Integer size, String sort, String direction);

    DTO findById(UUID id);

    DTO save(T object);

    Page<DTO> search (String value, Integer page, Integer size, String sort, String direction);

    boolean validate(T object);
}