package io.github.amandajuchem.projetoapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
    @Column(name = "descricao", columnDefinition = "VARCHAR")
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