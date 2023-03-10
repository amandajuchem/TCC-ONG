package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.enums.Motivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * The type Atendimento.
 */
@Data
@SuperBuilder
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

    @NotEmpty
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @NotEmpty
    @Column(name = "posologia", columnDefinition = "TEXT")
    private String posologia;

    @OneToMany
    private Set<Exame> exames;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> documentos;

    @OneToOne
    private Animal animal;

    @OneToOne
    private Usuario veterinario;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}