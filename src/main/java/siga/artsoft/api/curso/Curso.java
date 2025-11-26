package siga.artsoft.api.curso;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import siga.artsoft.api.departamento.Departamento;
import siga.artsoft.api.faculdade.Faculdade;
import siga.artsoft.api.nivelhabilitacao.NivelHabilitacao;
import siga.artsoft.api.regimecurso.RegimeCurso;
import siga.artsoft.api.utils.IdEntity;

@Table(name="curso")
@Entity(name="Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Curso extends IdEntity {

    private String nome;
    private String coordenador;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="faculdade_id")
    private Faculdade faculdade;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="departamento_id")
    private Departamento departamento;
    @ManyToOne
    @JoinColumn(name="nivelhabilitacao_id")
    private NivelHabilitacao nivelhab;
    @ManyToOne
    @JoinColumn(name="regimecurso_id")
    private RegimeCurso regimeCurso;

    private int duracao;
    private int credito_maximo;
    private int credito_minimo;
}
