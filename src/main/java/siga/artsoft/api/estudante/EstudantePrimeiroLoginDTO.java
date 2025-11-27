package siga.artsoft.api.estudante;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudantePrimeiroLoginDTO {

    @NotNull(message = "O email não pode ser nulo.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    @JsonProperty("dataNascimento") // camelCase do front
    private LocalDateTime dataNascimento;

    @NotBlank(message = "O nome é obrigatório.")
    @JsonProperty("nome") // camelCase do front
    private String nome;

    @NotBlank(message = "O apelido é obrigatório.")
    private String apelido;

    @NotNull(message = "O sexo é obrigatório.")
    @JsonProperty("sexoId") // camelCase do front
    private Long sexoId;

    @NotNull(message = "O NUIT é obrigatório.")
    private Long nuit;

    @NotNull(message = "O estado civil é obrigatório.")
    @JsonProperty("estadoCivilId") // camelCase do front
    private Long estadoCivilId;

    @NotBlank(message = "O número do documento de identificação é obrigatório.")
    @JsonProperty("numeroDocumentoIdentificacao") // camelCase do front
    private String numeroDocumentoIdentificacao;

    @NotNull(message = "O tipo de documento é obrigatório.")
    @JsonProperty("tipoDocumentoIdentificacaoId") // camelCase do front
    private Long tipoDocumentoIdentificacaoId;

    @NotNull(message = "A nacionalidade é obrigatória.")
    @JsonProperty("nacionalidadeId") // camelCase do front
    private Long nacionalidadeId;

    @NotNull(message = "O distrito é obrigatório.")
    @JsonProperty("distritoId") // camelCase do front
    private Long distritoId;

     @NotNull(message = "A provincia de Conclusao do Ensino Médio é obrigatório.")
     @JsonProperty("provinciaConclusaoEnsinoMedioId")
     private Long provinciaConclusaoEnsinoMedioId;
}
