package siga.artsoft.api.pauta;

import jakarta.validation.constraints.NotNull;

public record DadosPesquisaHistoricoAcademico(
       @NotNull
       Long idEstudante
) {
}
