package siga.artsoft.api.estudante;

import jakarta.validation.constraints.NotNull;

public record DadosPesquisaEstudante(

        @NotNull
        Long idUtilizador
) {
}
