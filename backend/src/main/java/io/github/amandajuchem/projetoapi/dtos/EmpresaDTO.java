package io.github.amandajuchem.projetoapi.dtos;

import io.github.amandajuchem.projetoapi.entities.Empresa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EmpresaDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String nome,
        String cnpj,
        List<TelefoneDTO> telefones,
        EnderecoDTO endereco
) implements Serializable {

    public static EmpresaDTO toDTO(Empresa empresa) {

        return new EmpresaDTO(
                empresa.getId(),
                empresa.getCreatedDate(),
                empresa.getLastModifiedDate(),
                empresa.getCreatedByUser(),
                empresa.getModifiedByUser(),
                empresa.getNome(),
                empresa.getCnpj(),
                empresa.getTelefones() != null ? empresa.getTelefones().stream().map(TelefoneDTO::toDTO).toList() : null,
                empresa.getEndereco() != null ? EnderecoDTO.toDTO(empresa.getEndereco()) : null
        );
    }
}