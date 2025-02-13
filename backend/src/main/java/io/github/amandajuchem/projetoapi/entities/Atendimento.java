package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.enums.Motivo;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_atendimentos")
public class Atendimento extends AbstractEntity {

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @NotNull
    @Column(name = "motivo", length = 25)
    @Enumerated(EnumType.STRING)
    private Motivo motivo;

    @Column(name = "diagnostico", columnDefinition = "VARCHAR")
    private String diagnostico;

    @Column(name = "posologia", columnDefinition = "VARCHAR")
    private String posologia;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ExameRealizado> examesRealizados;

    @OneToOne
    private Animal animal;

    @OneToOne
    private Usuario veterinario;
}