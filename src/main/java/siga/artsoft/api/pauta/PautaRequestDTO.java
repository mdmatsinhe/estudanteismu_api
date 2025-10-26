package siga.artsoft.api.pauta;

import jakarta.validation.constraints.NotNull;

public record PautaRequestDTO( @NotNull
                               Long idEstudante,

                               Long idDisciplina,

                               int anoLectivo,

                               int semestre,
                               Long idTurma) {

}
