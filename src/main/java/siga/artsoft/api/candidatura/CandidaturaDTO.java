package siga.artsoft.api.candidatura;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class CandidaturaDTO {

    // === Dados do Curso ===
    @NotNull(message = "A delegação é obrigatória.")
    private Long delegacaoId;

    @NotNull(message = "A faculdade é obrigatória.")
    private Long faculdadeId;

    @NotNull(message = "O curso é obrigatório.")
    private Long cursoId;

    @NotNull(message = "O turno é obrigatório.")
    private Long turnoId;

    // === Dados Pessoais ===
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
    private String nome;

    @NotBlank(message = "O apelido é obrigatório.")
    @Size(max = 100, message = "O apelido não pode exceder 100 caracteres.")
    private String apelido;

    @NotNull(message = "A data de nascimento é obrigatória.")
    private LocalDate dataNascimento;

    @NotNull(message = "O sexo é obrigatório.")
    private Long sexoId;

    @NotBlank(message = "O telemóvel é obrigatório.")
    @Pattern(
            regexp = "^(82|83|84|85|86|87)\\d{7}$",
            message = "O telemóvel deve ter 9 dígitos e começar por 82, 83, 84, 85, 86 ou 87."
    )
    private String telefone;

    private  String telefoneFixo;

    @Email(message = "O email informado não é válido.")
    private String email;

    // === Documento de Identificação ===
    @NotNull(message = "O tipo de documento é obrigatório.")
    private Long tipoDocId;

    @NotBlank(message = "O número do documento é obrigatório.")
    @Size(max = 50, message = "O número do documento é muito longo.")
    private String numeroDoc;

    // === Nacionalidade e Morada ===
    @NotNull(message = "A nacionalidade é obrigatória.")
    private Long nacionalidadeId;

    @NotNull(message = "A província é obrigatória.")
    private Long provinciaId;

    @NotNull(message = "O distrito é obrigatório.")
    private Long distritoId;

//    @NotBlank(message = "O endereço é obrigatório.")
    private String endereco;

    private String bairro;
    private String cidade;
    private String estado;
    private String instituicao;
    private String nuit;

    // === Filiação ===
    @NotBlank(message = "O nome do pai é obrigatório.")
    private String nomePai;

    @Pattern(
            regexp = "^(82|83|84|85|86|87)\\d{7}$",
            message = "O telefone do pai deve ter 9 dígitos e começar por 82, 83, 84, 85, 86 ou 87."
    )
    private String telefonePai; // NÃO obrigatório

    @NotBlank(message = "O nome da mãe é obrigatório.")
    private String nomeMae;

    @Pattern(
            regexp = "^(82|83|84|85|86|87)\\d{7}$",
            message = "O telefone da mãe deve ter 9 dígitos e começar por 82, 83, 84, 85, 86 ou 87."
    )
    private String telefoneMae; // NÃO obrigatório

    @NotBlank(message = "O tipo de encarregado é obrigatório.")
    private String tipoEncarregado;

    private String nomeEncarregado; // opcional, só se tipoEncarregado == "Outro"

    @Pattern(
            regexp = "^(82|83|84|85|86|87)\\d{7}$",
            message = "O telefone do encarregado deve ter 9 dígitos e começar por 82, 83, 84, 85, 86 ou 87."
    )
    private String telefoneEncarregado; // NÃO obrigatório

    private Integer anoCandidatura;
    private String numeroCandidato;
}
