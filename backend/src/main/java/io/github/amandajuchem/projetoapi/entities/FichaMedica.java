package io.github.amandajuchem.projetoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_fichas_medicas")
public class FichaMedica extends AbstractEntity {

    @NotEmpty
    @Column(name = "comorbidades", columnDefinition = "TEXT")
    private String comorbidades;

    @NotNull
    @Column(name = "castrado")
    private Boolean castrado;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}