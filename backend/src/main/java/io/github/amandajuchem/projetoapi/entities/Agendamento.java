package io.github.amandajuchem.projetoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The type Castracao.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_agendamentos")
public class Agendamento extends AbstractEntity {

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @NotEmpty
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @OneToOne
    private Animal animal;

    @OneToOne
    private Usuario veterinario;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}