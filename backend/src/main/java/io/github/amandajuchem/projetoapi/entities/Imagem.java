package io.github.amandajuchem.projetoapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The type Imagem.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_imagens")
public class Imagem extends AbstractEntity {

    @NotEmpty
    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "adocao_id")
    private Adocao adocao;

    @OneToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference(value = "jsonReferenceAnimal")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @OneToOne
    @JoinColumn(name = "tutor_id")
    @JsonBackReference(value = "jsonReferenceTutor")
    private Tutor tutor;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference(value = "jsonReferenceUsuario")
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}