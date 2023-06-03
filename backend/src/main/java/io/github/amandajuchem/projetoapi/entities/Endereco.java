package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.enums.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Endereco.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_enderecos")
public class Endereco extends AbstractEntity {

    @NotEmpty
    @Column(name = "rua", length = 100)
    private String rua;

    @NotNull
    @Column(name = "numero_residencia", length = 10)
    private String numeroResidencia;

    @NotEmpty
    @Column(name = "bairro", length = 50)
    private String bairro;

    @NotEmpty
    @Column(name = "cidade", length = 100)
    private String cidade;

    @NotNull
    @Column(name = "estado", length = 25)
    @Enumerated(value = EnumType.STRING)
    private Estado estado;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @NotEmpty
    @Column(name = "cep", length = 8)
    private String cep;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}