package siga.artsoft.api.pauta;

import jakarta.validation.constraints.NotNull;

public record DadosPesquisaNotas(

        @NotNull
        Long idEstudante,

        @NotNull
        int anoLectivo,

        @NotNull
        int semestre) {

}
