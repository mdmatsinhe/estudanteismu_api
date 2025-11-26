package siga.artsoft.api.candidatura;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.delegacao.Delegacao;
import siga.artsoft.api.distrito.Distrito;
import siga.artsoft.api.faculdade.Faculdade;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.provincia.Provincia;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacao;
import siga.artsoft.api.turno.Turno;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "candidatura",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"numero_doc", "ano_candidatura"})
        }
)
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_candidato", unique = true, nullable = false)
    private String numeroCandidato;

    @Column(name = "ano_candidatura", nullable = false)
    private Integer anoCandidatura;

    @ManyToOne
    @JoinColumn(name = "delegacao_id")
    private Delegacao delegacao;

    @ManyToOne
    @JoinColumn(name = "faculdade_id")
    private Faculdade faculdade;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    private Turno turno;

    private String nome;
    private String apelido;
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "sexo_id")
    private Sexo sexo;

    private String telefoneFixo;
    private String telefone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "tipo_doc_id")
    private TipoDocumentoIdentificacao tipoDoc;

    @Column(name = "numero_doc", nullable = false)
    private String numeroDoc;

    @ManyToOne
    @JoinColumn(name = "nacionalidade_id")
    private Nacionalidade nacionalidade;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @ManyToOne
    @JoinColumn(name = "distrito_id")
    private Distrito distrito;

    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;

    private String nomePai;
    private String telefonePai;
    private String nomeMae;
    private String telefoneMae;

    private String tipoEncarregado;
    private String nomeEncarregado;
    private String telefoneEncarregado;

    private String instituicao;
    private String nuit;
    private LocalDateTime createdAt = LocalDateTime.now();
}
