package siga.artsoft.api.inscricao;

import jakarta.validation.constraints.NotNull;

public record DadosPesquisaInscricao(
        @NotNull
        Long idEstudante,

        @NotNull
        int anoLectivo,

        @NotNull
        int semestre
) {
}
