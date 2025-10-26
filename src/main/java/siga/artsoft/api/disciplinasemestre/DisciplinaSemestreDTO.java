package siga.artsoft.api.disciplinasemestre;

public class DisciplinaSemestreDTO {

    private Long id;
    private String nomeDisciplina;
    private int semestreCurso;
    private int ano;
    private int semestre;
    private double creditos;

    public DisciplinaSemestreDTO(Long id, String nomeDisciplina, int semestreCurso, int ano, int semestre, double creditos) {
        this.id = id;
        this.nomeDisciplina = nomeDisciplina;
        this.semestreCurso = semestreCurso;
        this.ano = ano;
        this.semestre = semestre;
        this.creditos = creditos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public int getSemestreCurso() {
        return semestreCurso;
    }

    public void setSemestreCurso(int semestreCurso) {
        this.semestreCurso = semestreCurso;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }
}
