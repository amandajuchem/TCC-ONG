package io.github.amandajuchem.projetoapi.entities;

import io.github.amandajuchem.projetoapi.enums.Motivo;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @Column(name = "diagnostico", columnDefinition = "VARCHAR")
    private String diagnostico;

    @NotEmpty
    @Column(name = "posologia", columnDefinition = "VARCHAR")
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