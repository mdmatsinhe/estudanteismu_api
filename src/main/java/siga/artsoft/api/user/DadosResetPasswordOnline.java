package siga.artsoft.api.user;

import java.time.LocalDate;

public record DadosResetPasswordOnline(
        String codigoEstudante,
        LocalDate dataNascimento
) {
}
