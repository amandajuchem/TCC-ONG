package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.enums.Estado;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type Endereco.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_enderecos")
public class Endereco extends AbstractEntity {

    @NotEmpty
    @Column(name = "rua")
    private String rua;

    @NotNull
    @Column(name = "numero_residencia")
    private String numeroResidencia;

    @NotEmpty
    @Column(name = "bairro")
    private String bairro;

    @NotEmpty
    @Column(name = "cidade")
    private String cidade;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "complemento")
    private String complemento;

    @NotEmpty
    @Column(name = "cep", length = 8)
    private String cep;
}