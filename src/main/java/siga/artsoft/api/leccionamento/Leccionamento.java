package siga.artsoft.api.leccionamento;

import jakarta.persistence.*;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.funcionario.Funcionario;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.turno.Turno;
import siga.artsoft.api.utils.IdEntity;

@Table(name="leccionamento")
@Entity(name="Leccionamento")
public class Leccionamento extends IdEntity {

    private static final long serialVersionUID = -3369584122652913752L;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private SessaoTurma turma;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Funcionario docente;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private DisciplinaSemestre disciplina;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    private Turno turno;

    @Column(name="ano_lectivo")
    private int anoLectivo;
    private int semestre;
    @Column(name="horas_semanais")
    private int horasSemanais;
    @Column(name="plano_avaliacoes")
    private boolean planoAvaliacoes=false;
    @Column(name="forma_calculo_media")
    private String formaCalculoMedia;


    public Turno getTurno() {
        return turno;
    }
    public void setTurno(Turno turno) {
        this.turno = turno;
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
    public SessaoTurma getTurma() {
        return turma;
    }
    public void setTurma(SessaoTurma turma) {
        this.turma = turma;
    }
    public Funcionario getDocente() {
        return docente;
    }
    public void setDocente(Funcionario docente) {
        this.docente = docente;
    }
    public DisciplinaSemestre getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(DisciplinaSemestre disciplina) {
        this.disciplina = disciplina;
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
    public int getHorasSemanais() {
        return horasSemanais;
    }
    public void setHorasSemanais(int horasSemanais) {
        this.horasSemanais = horasSemanais;
    }
    public boolean isPlanoAvaliacoes() {
        return planoAvaliacoes;
    }
    public void setPlanoAvaliacoes(boolean planoAvaliacoes) {
        this.planoAvaliacoes = planoAvaliacoes;
    }
    public String getFormaCalculoMedia() {
        return formaCalculoMedia;
    }
    public void setFormaCalculoMedia(String formaCalculoMedia) {
        this.formaCalculoMedia = formaCalculoMedia;
    }
}
