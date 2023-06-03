package io.github.amandajuchem.projetoapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_telefones")
public class Telefone extends AbstractEntity {

    @NotEmpty
    @Column(name = "numero", length = 11)
    private String numero;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}