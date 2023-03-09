package io.github.amandajuchem.projetoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_observacoes")
public class Observacao extends AbstractEntity {

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}