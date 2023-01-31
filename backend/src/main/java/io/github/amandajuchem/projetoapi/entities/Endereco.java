package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.amandajuchem.projetoapi.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @OneToOne
    @JoinColumn(name = "tutor_id")
    @JsonBackReference(value = "jsonReferenceEnderecoTutor")
    private Tutor tutor;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}