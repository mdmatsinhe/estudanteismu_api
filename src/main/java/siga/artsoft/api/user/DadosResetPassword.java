package siga.artsoft.api.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosResetPassword(

        @NotNull
        long id,

        @NotBlank
        String password,
        int loginCount
) {
}
