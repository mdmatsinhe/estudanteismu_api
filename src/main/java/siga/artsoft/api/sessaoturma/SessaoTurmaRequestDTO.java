package siga.artsoft.api.sessaoturma;

import jakarta.validation.constraints.NotNull;

public class SessaoTurmaRequestDTO {

    @NotNull
    private Long cursoId;

    @NotNull
    private Long curriculoId;

    @NotNull
    private Long anoCursoId;

    @NotNull
    private Long semestreId;

    @NotNull
    private Long turnoId;

    @NotNull
    private Integer anoLectivo;

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Long getCurriculoId() {
        return curriculoId;
    }

    public void setCurriculoId(Long curriculoId) {
        this.curriculoId = curriculoId;
    }

    public Long getAnoCursoId() {
        return anoCursoId;
    }

    public void setAnoCursoId(Long anoCursoId) {
        this.anoCursoId = anoCursoId;
    }

    public Long getSemestreId() {
        return semestreId;
    }

    public void setSemestreId(Long semestreId) {
        this.semestreId = semestreId;
    }

    public Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }
}
