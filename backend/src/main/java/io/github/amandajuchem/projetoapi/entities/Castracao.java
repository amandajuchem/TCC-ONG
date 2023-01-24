package io.github.amandajuchem.projetoapi.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The type Castracao.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_castracoes")
public class Castracao extends AbstractEntity {

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @OneToOne
    private Animal animal;

    @OneToOne
    private Usuario usuario;
}