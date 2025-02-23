package io.github.amandajuchem.projetoapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_fichas_medicas")
public class FichaMedica extends AbstractEntity {

    @NotEmpty
    @Column(name = "comorbidades", columnDefinition = "VARCHAR")
    private String comorbidades;

    @NotNull
    @Column(name = "castrado")
    private Boolean castrado;
}