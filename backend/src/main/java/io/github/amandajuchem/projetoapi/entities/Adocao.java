package io.github.amandajuchem.projetoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Adocao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_adocoes")
public class Adocao extends AbstractEntity {

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @NotNull
    @Column(name = "vale_castracao")
    private Boolean valeCastracao;

    @OneToOne
    private Animal animal;

    @OneToMany(mappedBy = "adocao")
    private Set<Imagem> termoResponsabilidade;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}