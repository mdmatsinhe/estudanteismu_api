package siga.artsoft.api.user;

import java.time.LocalDateTime;

public record DadosLoginResponse(String token,
                                 Long idEstudante,
                                 long numero,
                                 String nome,
                                 String apelido,
                                 String curso,
                                 Long userId,
                                 String endereco,
                                 Long telefone,
                                 String numeroDocumentoIdentificacao,
                                 LocalDateTime dataNascimento,
                                 String email,
                                 String distrito,
                                 String provincia,
                                 String sexo,
                                 String nacionalidade

) {
}
