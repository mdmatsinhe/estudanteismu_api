package siga.artsoft.api.estudante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import siga.artsoft.api.antecedenteescolar.AntecedenteEscolar;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.distrito.Distrito;
import siga.artsoft.api.estadocivil.EstadoCivil;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.provincia.Provincia;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.situacaoacademica.SituacaoAcademica;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacao;
import siga.artsoft.api.turno.Turno;
import siga.artsoft.api.user.User;
import siga.artsoft.api.utils.IdEntity;
import siga.artsoft.api.viaingresso.ViaIngresso;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name="estudante")
@Entity(name="Estudante")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Estudante extends IdEntity {

    private long numero;
    private String apelido;
    private String nome;
    @Column(name = "numero_documento_identificacao")
    private String numeroDocumentoIdentificacao;
    @Column (name = "nome_completo", insertable = false, updatable = false)
    private String nomeCompleto;
    private Long nuit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    @JsonIgnore
    private Curso curso;

    @Column (name = "data_nascimento")
    private LocalDateTime dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_documento_identificacao_id")
    @JsonIgnore
    private TipoDocumentoIdentificacao tipoDocumentoIdentificacao;

    @Column (name = "validade_documento_identificacao")
    private LocalDateTime validadeDocumentoIdentificacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadocivil_id")
    @JsonIgnore
    private EstadoCivil estadoCivil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacionalidade_id")
    @JsonIgnore
    private Nacionalidade nacionalidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distrito_id")
    @JsonIgnore
    private Distrito distrito;

    private byte[] foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    @JsonIgnore
    private Curriculo curriculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viaingresso_id")
    @JsonIgnore
    private ViaIngresso viaIngresso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situacaoacademica_id")
    @JsonIgnore
    private SituacaoAcademica situacaoAcademica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexo_id")
    @JsonIgnore
    private Sexo sexo;

    private String endereco;
    private long telefone;

    @Column(columnDefinition = "varchar(255) default ' '")
    private String email;
    @Column (name = "nome_mae")
    private String nomeMae;
    @Column (name = "nome_pai")
    private String nomePai;
    @Column (name = "ano_ingresso")
    private int anoIngresso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_id")
    @JsonIgnore
    private Turno turno;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="estudante", cascade=CascadeType.MERGE,orphanRemoval = true)
    @JsonIgnore
    List<AntecedenteEscolar> antecedente=new ArrayList<AntecedenteEscolar>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User userLogin;

    @Column(name = "fds", columnDefinition = "boolean default false")
    private boolean FDS;

    private boolean bolseiro;

    @Column(name = "dados_actualizados", nullable = false)
    private boolean dadosActualizados;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_conclusao_ensino_medio")
    @JsonIgnore
    private Provincia provinciaConclusaoEnsinoMedio;
}
