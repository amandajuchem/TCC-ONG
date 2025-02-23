package io.github.amandajuchem.projetoapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_feiras_adocao")
public class FeiraAdocao extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome", length = 100, unique = true)
    private String nome;

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @ManyToMany
    @ToString.Exclude
    private Set<Animal> animais;

    @ManyToMany
    @ToString.Exclude
    private Set<Usuario> usuarios;
}