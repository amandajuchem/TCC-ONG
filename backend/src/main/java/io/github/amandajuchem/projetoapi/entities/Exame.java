package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_exames")
public class Exame extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 100)
    private String nome;

    @NotEmpty
    @Column(name = "categoria", length = 25)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "atendimento_id")
    @JsonBackReference(value = "jsonReferenceExamesAtendimento")
    private Atendimento atendimento;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}