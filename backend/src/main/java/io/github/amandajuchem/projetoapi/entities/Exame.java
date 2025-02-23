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
@Entity(name = "tb_exames")
public class Exame extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 100, unique = true)
    private String nome;

    @NotEmpty
    @Column(name = "categoria", length = 25)
    private String categoria;
}