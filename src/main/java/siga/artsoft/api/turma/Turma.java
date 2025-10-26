package siga.artsoft.api.turma;

import jakarta.persistence.*;
import siga.artsoft.api.anocurso.AnoCurso;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.semestreano.SemestreAno;
import siga.artsoft.api.turno.Turno;
import siga.artsoft.api.utils.IdEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Table(name="turma")
@Entity(name="Turma")
public class Turma extends IdEntity {

    private static final long serialVersionUID = -3369584122652913752L;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    @ManyToOne
    @JoinColumn(name = "ano_id")
    private AnoCurso ano;

    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private SemestreAno semestre;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    private Turno turno;

    @ManyToMany
    @JoinTable(name = "turma_disciplina",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
    private List<DisciplinaSemestre> disciplinas = new ArrayList<DisciplinaSemestre>();

    private int semestre_curso=0;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public AnoCurso getAno() {
        return ano;
    }

    public void setAno(AnoCurso ano) {
        this.ano = ano;
    }

    public SemestreAno getSemestre() {
        return semestre;
    }

    public void setSemestre(SemestreAno semestre) {
        this.semestre = semestre;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public List<DisciplinaSemestre> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<DisciplinaSemestre> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void addDisciplinas(DisciplinaSemestre disciplina) {
        disciplinas.add(disciplina);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((curso == null) ? 0 : curso.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
        Turma other = (Turma) obj;
        if (curso == null) {
            if (other.curso != null)
                return false;
        } else if (!curso.equals(other.curso))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    public int getSemestreCurso() {

        if ((ano.getCodigo() == 1) && (semestre.getCodigo() == 1)) {
            semestre_curso=1;
        }
        if ((ano.getCodigo() == 1) && (semestre.getCodigo() == 2)) {
            semestre_curso=2;
        }
        if ((ano.getCodigo() == 2) && (semestre.getCodigo() == 1)) {
            semestre_curso=3;
        }
        if ((ano.getCodigo() == 2) && (semestre.getCodigo() == 2)) {
            semestre_curso=4;
        }
        if ((ano.getCodigo() == 3) && (semestre.getCodigo() == 1)) {
            semestre_curso=5;
        }
        if ((ano.getCodigo() == 3) && (semestre.getCodigo() == 2)) {
            semestre_curso=6;
        }
        if ((ano.getCodigo() == 4) && (semestre.getCodigo() == 1)) {
            semestre_curso=7;
        }
        if ((ano.getCodigo() == 4) && (semestre.getCodigo() == 2)) {
            semestre_curso=8;
        }
        if ((ano.getCodigo() == 5) && (semestre.getCodigo() == 1)) {
            semestre_curso=9;
        }
        if ((ano.getCodigo() == 5) && (semestre.getCodigo() == 2)) {
            semestre_curso=10;
        }
        return semestre_curso;
    }

    public void setSemestreCurso(int semestreCurso) {
        this.semestre_curso = semestreCurso;
    }

    public static Comparator<Turma> TurmaNomeComparator = new Comparator<Turma>() {

        public int compare(Turma turma1, Turma turma2) {

            String nome1 = turma1.getNome().toUpperCase();
            String nome2 = turma2.getNome().toUpperCase();

            // ascending order
            return nome1.compareTo(nome2);

            // descending order
            // return fruitName2.compareTo(fruitName1);
        }

    };

}
