package io.github.amandajuchem.projetoapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Feira adocao.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_feiras_adocao")
public class FeiraAdocao extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 100)
    private String nome;

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @OneToMany
    private Set<Animal> animais;

    @OneToMany
    private Set<Usuario> usuarios;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}