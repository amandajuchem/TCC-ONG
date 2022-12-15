package io.github.amandajuchem.projetoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Atendimento.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_atendimentos")
public class Atendimento extends AbstractEntity {

    @NotNull
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "data_hora_retorno")
    private LocalDateTime dataHoraRetorno;

    @NotEmpty
    @Column(name = "motivo")
    private String motivo;

    @NotEmpty
    @Column(name = "comorbidades", columnDefinition = "TEXT")
    private String comorbidades;

    @NotEmpty
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @NotEmpty
    @Column(name = "exames")
    private String exames;

    @NotEmpty
    @Column(name = "procedimentos")
    private String procedimentos;

    @NotEmpty
    @Column(name = "posologia")
    private String posologia;

    @OneToMany(mappedBy = "atendimento")
    private Set<Imagem> documentos;

    @OneToOne
    private Animal animal;

    @OneToOne
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}