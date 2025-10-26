package siga.artsoft.api.sessaoturma;

public class SessaoTurmaDTO {

    private Long id;
    private String nome;
    private Long curriculoId;
    private int semestreCurso;

    public SessaoTurmaDTO(Long id, String nome, Long curriculoId, int semestreCurso) {
        this.id = id;
        this.nome = nome;
        this.curriculoId = curriculoId;
        this.semestreCurso = semestreCurso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCurriculoId() {
        return curriculoId;
    }

    public void setCurriculoId(Long curriculoId) {
        this.curriculoId = curriculoId;
    }

    public int getSemestreCurso() {
        return semestreCurso;
    }

    public void setSemestreCurso(int semestreCurso) {
        this.semestreCurso = semestreCurso;
    }
}
