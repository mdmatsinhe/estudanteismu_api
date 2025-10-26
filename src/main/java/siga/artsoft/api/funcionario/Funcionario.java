package siga.artsoft.api.funcionario;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import siga.artsoft.api.categoriafuncionario.CategoriaFuncionario;
import siga.artsoft.api.departamento.Departamento;
import siga.artsoft.api.estadoactividade.EstadoActividade;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.nivelacademico.NivelAcademico;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.tipocontrato.TipoContrato;
import siga.artsoft.api.tipofuncionario.TipoFuncionario;
import siga.artsoft.api.tiposituacaocontratual.TipoSituacaoContratual;
import siga.artsoft.api.user.User;
import siga.artsoft.api.utils.IdEntity;

import java.util.Date;

@Table(name="funcionario")
@Entity(name="Funcionario")
public class Funcionario extends IdEntity {

    private static final long serialVersionUID = -3055136650749153077L;
    private long nuit;
    private String apelido;
    private String nome;
    private String endereco;
    private long telefone;
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipocontrato_id")
    private TipoContrato tipoContrato;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivelacademico_id")
    private NivelAcademico nivelAcademico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private CategoriaFuncionario categoria;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_funcionario")
    private TipoFuncionario tipoFuncionario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nacionalidade_id")
    private Nacionalidade nacionalidade;

    private byte[] foto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "situacaocontratual_id")
    private TipoSituacaoContratual situacaoContratual;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sexo_id")
    private Sexo sexo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estadoactividade_id")
    private EstadoActividade estadoActividade;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_id")
    private User userLogin;


    public User getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(User userLogin) {
        this.userLogin = userLogin;
    }

    public long getNuit() {
        return nuit;
    }

    public void setNuit(long nuit) {
        this.nuit = nuit;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public NivelAcademico getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(NivelAcademico nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public CategoriaFuncionario getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaFuncionario categoria) {
        this.categoria = categoria;
    }

    public TipoFuncionario getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public TipoSituacaoContratual getSituacaoContratual() {
        return situacaoContratual;
    }

    public void setSituacaoContratual(TipoSituacaoContratual situacaoContratual) {
        this.situacaoContratual = situacaoContratual;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EstadoActividade getEstadoActividade() {
        return estadoActividade;
    }

    public void setEstadoActividade(EstadoActividade estadoActividade) {
        this.estadoActividade = estadoActividade;
    }

}
