package siga.artsoft.api.contacorrente;

import jakarta.validation.constraints.NotNull;

public record DadosPesquisaContaCorrente(
        @NotNull
        Long idEstudante,

        @NotNull
        int anoLectivo,

        @NotNull
        int semestre
) {
}
