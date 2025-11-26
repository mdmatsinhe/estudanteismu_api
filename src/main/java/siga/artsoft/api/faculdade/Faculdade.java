package siga.artsoft.api.faculdade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import siga.artsoft.api.delegacao.Delegacao;
import siga.artsoft.api.utils.IdEntity;

@Table(name="faculdade")
@Entity(name="Facudade")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Faculdade extends IdEntity {
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delegacao")
    private Delegacao delegacao;
    private String nome;
    private String nome_abreviado;
    private String director;
    private String endereco;
}
