package io.github.amandajuchem.projetoapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_telefones")
public class Telefone extends AbstractEntity {

    @NotEmpty
    @Column(name = "numero", length = 11)
    private String numero;
}