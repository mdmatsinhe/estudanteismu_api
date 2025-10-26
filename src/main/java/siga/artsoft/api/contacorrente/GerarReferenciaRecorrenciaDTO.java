package siga.artsoft.api.contacorrente;

public class GerarReferenciaRecorrenciaDTO {

    private Long idEstudante;
    private Long idPauta;
    private Long idUser;
    private int anoLectivo;
    private int semestre;

    public Long getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(Long idEstudante) {
        this.idEstudante = idEstudante;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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
}
