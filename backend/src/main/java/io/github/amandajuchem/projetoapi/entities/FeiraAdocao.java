package io.github.amandajuchem.projetoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Feira adocao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_feiras_adocao")
public class FeiraAdocao extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome")
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