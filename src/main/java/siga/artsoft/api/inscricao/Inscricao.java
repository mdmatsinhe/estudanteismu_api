package siga.artsoft.api.inscricao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.utils.IdEntity;

import java.util.Date;

@Table(name="inscricao")
@Entity(name="Inscricao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inscricao extends IdEntity {

    private static final long serialVersionUID = 8937774419442875519L;

    @ManyToOne
    private Estudante estudante;

    @ManyToOne
    private SessaoTurma turma;

    @ManyToOne
    @JoinColumn(name = "disciplina_semestre_id")
    private DisciplinaSemestre disciplinaSemestre;

    @Column(name = "ano_lectivo")
    private int anoLectivo;
    private int semestre;
    @Column (name = "semestre_curso")
    private int semestreCurso;
    @Column (name = "nota_exame_normal")
    private double notaExameNormal;
    @Column (name = "nota_exame_recorrencia")
    private double notaExameRecorrencia;
    @Column (name = "nota_frequencia")
    private double notaFrequencia;
    @Column (name = "nota_final")
    private double notaFinal;

    @Column (name = "tipo_inscricao")
    private String tipoInscricao;
    private boolean graded;
    private Date dia;
    private String status;

}
