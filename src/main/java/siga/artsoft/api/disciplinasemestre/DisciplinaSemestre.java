package siga.artsoft.api.disciplinasemestre;

import jakarta.persistence.*;
import siga.artsoft.api.anocurso.AnoCurso;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.disciplina.Disciplina;
import siga.artsoft.api.semestreano.SemestreAno;
import siga.artsoft.api.utils.IdEntity;


@Table(name="planoestudos")
@Entity(name="DisciplinaSemestre")
public class DisciplinaSemestre extends IdEntity {

    private static final long serialVersionUID = 8741355248630718268L;

    @ManyToOne
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    @ManyToOne
    @JoinColumn(name = "anocurso_id")
    private AnoCurso anoCurso;

    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private SemestreAno semestreAno;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @Column(name="semestre_curso")
    private int semestreCurso;

    @Column(name="carga_horaria")
    private double cargaHoraria;

    private double creditos;

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public AnoCurso getAnoCurso() {
        return anoCurso;
    }

    public void setAnoCurso(AnoCurso anoCurso) {
        this.anoCurso = anoCurso;
    }

    public SemestreAno getSemestreAno() {
        return semestreAno;
    }

    public void setSemestreAno(SemestreAno semestreAno) {
        this.semestreAno = semestreAno;
    }

    public int getSemestreCurso() {
        return semestreCurso;
    }

    public void setSemestreCurso(int semestreCurso) {
        this.semestreCurso = semestreCurso;
    }

    public int setSemestreCurso() {

        if ((anoCurso.getCodigo() == 1) && (semestreAno.getCodigo() == 1)) {
            return 1;
        }
        if ((anoCurso.getCodigo() == 1) && (semestreAno.getCodigo() == 2)) {
            return 2;
        }
        if ((anoCurso.getCodigo() == 2) && (semestreAno.getCodigo() == 1)) {
            return 3;
        }
        if ((anoCurso.getCodigo() == 2) && (semestreAno.getCodigo() == 2)) {
            return 4;
        }
        if ((anoCurso.getCodigo() == 3) && (semestreAno.getCodigo() == 1)) {
            return 5;
        }
        if ((anoCurso.getCodigo() == 3) && (semestreAno.getCodigo() == 2)) {
            return 6;
        }
        if ((anoCurso.getCodigo() == 4) && (semestreAno.getCodigo() == 1)) {
            return 7;
        }
        if ((anoCurso.getCodigo() == 4) && (semestreAno.getCodigo() == 2)) {
            return 8;
        }
        if ((anoCurso.getCodigo() == 5) && (semestreAno.getCodigo() == 1)) {
            return 9;
        }
        if ((anoCurso.getCodigo() == 5) && (semestreAno.getCodigo() == 2)) {
            return 10;
        }
        return 0;
    }

    public double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((disciplina == null) ? 0 : disciplina.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DisciplinaSemestre other = (DisciplinaSemestre) obj;
        if (disciplina == null) {
            if (other.disciplina != null)
                return false;
        } else if (!disciplina.equals(other.disciplina))
            return false;
        return true;
    }

}
