package siga.artsoft.api.utils;

public enum AreaCurso {

    CIENCIAS_TECNOLOGICAS("CT"),
    CIENCIAS_SOCIAIS_HUMANIDADES("CSH");

    private final String abreviatura;

    AreaCurso(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
