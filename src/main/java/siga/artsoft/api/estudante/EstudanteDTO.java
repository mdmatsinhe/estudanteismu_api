package siga.artsoft.api.estudante;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class EstudanteDTO {

    private Long id;
    private String nome;
    private String apelido;
    private Long numero;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataNascimento;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime validadeDocumentoIdentificacao;
    private String numeroDocumentoIdentificacao;
    private Long distrito;
    private Long estadoCivil;
    private Long sexo;
    private String nomePai;
    private String nomeMae;
    private String email;
    private Long nacionalidade;
    private Long telefone;
    private Long provinciaId;
    private Long tipoDocumentoIdentificacao;
    private String endereco;
    private Long nuit;
    private boolean bolseiro;

    // Construtor vazio para uso com bibliotecas de serialização/deserialização (ex: Jackson)
    public EstudanteDTO() {}

    // Construtor que inicializa a partir de uma entidade Estudante
    public EstudanteDTO(Estudante estudante) {
        this.id = estudante.getId();
        this.nome = estudante.getNome();
        this.apelido = estudante.getApelido();
        this.numero = estudante.getNumero();
        this.dataNascimento = estudante.getDataNascimento();
        this.validadeDocumentoIdentificacao = estudante.getValidadeDocumentoIDentificacao();
        this.numeroDocumentoIdentificacao = estudante.getNumeroDocumentoIdentificacao();
        this.distrito = estudante.getDistrito() != null ? estudante.getDistrito().getId() : null;
        this.estadoCivil = estudante.getEstadoCivil() != null ? estudante.getEstadoCivil().getId() : null;
        this.sexo = estudante.getSexo() != null ? estudante.getSexo().getId() : null;
        this.nomePai = estudante.getNomePai();
        this.nomeMae = estudante.getNomeMae();
        this.email = estudante.getEmail();
        this.nacionalidade = estudante.getNacionalidade() != null ? estudante.getNacionalidade().getId() : null;
        this.telefone = estudante.getTelefone();
        this.bolseiro = estudante.isBolseiro();
        this.provinciaId = estudante.getDistrito() != null && estudante.getDistrito().getProvincia() != null
                ? estudante.getDistrito().getProvincia().getId()
                : null;
        this.tipoDocumentoIdentificacao = estudante.getTipoDocumentoIdentificacao() != null
                ? estudante.getTipoDocumentoIdentificacao().getId()
                : null;
    }

    // Getters e Setters para todos os campos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDateTime getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDateTime getValidadeDocumentoIdentificacao() {
        return validadeDocumentoIdentificacao;
    }

    public void setValidadeDocumentoIdentificacao(LocalDateTime validadeDocumentoIdentificacao) {
        this.validadeDocumentoIdentificacao = validadeDocumentoIdentificacao;
    }

    public String getNumeroDocumentoIdentificacao() {
        return numeroDocumentoIdentificacao;
    }

    public void setNumeroDocumentoIdentificacao(String numeroDocumentoIdentificacao) {
        this.numeroDocumentoIdentificacao = numeroDocumentoIdentificacao;
    }

    public Long getDistrito() {
        return distrito;
    }

    public void setDistrito(Long distrito) {
        this.distrito = distrito;
    }

    public Long getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Long estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Long getSexo() {
        return sexo;
    }

    public void setSexo(Long sexo) {
        this.sexo = sexo;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Long nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Long getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(Long provinciaId) {
        this.provinciaId = provinciaId;
    }

    public Long getTipoDocumentoIdentificacao() {
        return tipoDocumentoIdentificacao;
    }

    public void setTipoDocumentoIdentificacao(Long tipoDocumentoIdentificacao) {
        this.tipoDocumentoIdentificacao = tipoDocumentoIdentificacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getNuit() {
        return nuit;
    }

    public void setNuit(Long nuit) {
        this.nuit = nuit;
    }

    public boolean isBolseiro() {
        return bolseiro;
    }

    public void setBolseiro(boolean bolseiro) {
        this.bolseiro = bolseiro;
    }
}
