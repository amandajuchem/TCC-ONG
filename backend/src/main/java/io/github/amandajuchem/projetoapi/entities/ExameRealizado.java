package io.github.amandajuchem.projetoapi.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_exames_realizados")
public class ExameRealizado extends AbstractEntity {

    @ManyToOne
    @ToString.Exclude
    private Exame exame;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Arquivo arquivo;
}