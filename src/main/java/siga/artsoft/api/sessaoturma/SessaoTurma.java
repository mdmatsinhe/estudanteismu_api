package siga.artsoft.api.sessaoturma;

import jakarta.persistence.*;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.funcionario.Funcionario;
import siga.artsoft.api.turma.Turma;
import siga.artsoft.api.turno.Turno;
import siga.artsoft.api.utils.IdEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Table(name="sessaoturma")
@Entity(name="SessaoTurma")
public class SessaoTurma extends IdEntity {

    private static final long serialVersionUID = -3369584122652913752L;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @Column(name = "ano_lectivo")
    private int anoLectivo;
    private int semestre;
    @Column(name = "nr_maximo_estudantes")
    private int nrMaximoEstudantes;
    @Column(name = "nr_estudantes_inscritos")
    private int nrEstudantesInscritos;

    private boolean open = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sessaoturma_estudante", joinColumns = @JoinColumn(name = "sessaoturma_id"), inverseJoinColumns = @JoinColumn(name = "estudante_id"))
    private List<Estudante> estudantes = new ArrayList<Estudante>();

    @ManyToMany
    @JoinTable(name = "sessaoturma_docente", joinColumns = @JoinColumn(name = "sessaoturma_id"), inverseJoinColumns = @JoinColumn(name = "funcionario_id"))
    private List<Funcionario> docentes = new ArrayList<Funcionario>();

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

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public int getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(int anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getNrMaximoEstudantes() {
        return nrMaximoEstudantes;
    }

    public void setNrMaximoEstudantes(int nrMaximoEstudantes) {
        this.nrMaximoEstudantes = nrMaximoEstudantes;
    }

    public List<Estudante> getEstudantes() {
        return estudantes;
    }

    public void setEstudantes(List<Estudante> estudantes) {
        this.estudantes = estudantes;
    }

    public List<Funcionario> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Funcionario> docentes) {
        this.docentes = docentes;
    }

    public void addDocentes(Funcionario docente) {
        docentes.add(docente);
    }

    public int getNrEstudantesInscritos() {
        return nrEstudantesInscritos;
    }

    public void setNrEstudantesInscritos(int nrEstudantesInscritos) {
        this.nrEstudantesInscritos = nrEstudantesInscritos;
    }

    public void addEstudantes(Estudante estudante) {

        estudantes.add(estudante);
        nrEstudantesInscritos++;
        if (nrEstudantesInscritos == nrMaximoEstudantes) {
            open = false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + anoLectivo;
        result = prime * result + ((curso == null) ? 0 : curso.hashCode());
        result = prime * result + semestre;
        result = prime * result + ((turma == null) ? 0 : turma.hashCode());
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
        SessaoTurma other = (SessaoTurma) obj;
        if (anoLectivo != other.anoLectivo)
            return false;
        if (curso == null) {
            if (other.curso != null)
                return false;
        } else if (!curso.equals(other.curso))
            return false;
        if (semestre != other.semestre)
            return false;
        if (turma == null) {
            if (other.turma != null)
                return false;
        } else if (!turma.equals(other.turma))
            return false;
        return true;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public static Comparator<SessaoTurma> SessaoTurmaNomeComparator = new Comparator<SessaoTurma>() {

        public int compare(SessaoTurma turma1, SessaoTurma turma2) {

            String nome1 = turma1.getTurma().getNome().toUpperCase();
            String nome2 = turma2.getTurma().getNome().toUpperCase();

            // ascending order
            return nome1.compareTo(nome2);

            // descending order
            // return fruitName2.compareTo(fruitName1);
        }

    };

}
