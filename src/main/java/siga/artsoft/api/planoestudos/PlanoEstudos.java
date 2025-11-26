package siga.artsoft.api.planoestudos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import siga.artsoft.api.anocurso.AnoCurso;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.disciplina.Disciplina;
import siga.artsoft.api.semestreano.SemestreAno;

import java.math.BigDecimal;

/// Entidade PlanoEstudos.java (CORRIGIDA)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "planoestudos")
public class PlanoEstudos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "carga_horaria")
    private Integer cargaHoraria;

    @Column(name = "creditos", precision = 19, scale = 2)
    private BigDecimal creditos;

    @Column(name = "semestre_curso")
    private Integer semestreCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anocurso_id")
    private AnoCurso anoCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semestre_id")
    private SemestreAno semestre;


    public SemestreAno getSemestre() {
        return semestre;
    }
    public void setSemestre(SemestreAno semestre) {
        this.semestre = semestre;
    }
}