package siga.artsoft.api.pauta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.leccionamento.Leccionamento;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.utils.IdEntity;

import java.util.Date;

@Table(name="pauta")
@Entity(name="Pauta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pauta extends IdEntity {

    private static final long serialVersionUID = -6139749719108547021L;

    @ManyToOne
    private Leccionamento leccionamento;

    @ManyToOne
    private Estudante estudante;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private DisciplinaSemestre disciplina;

    @ManyToOne
    @JoinColumn(name = "turma_id",nullable = false)
    private SessaoTurma turma;

    @Column(name="ano_lectivo",nullable = false)
    private int anoLectivo;
    @Column(name="semestre",nullable = false)
    private int semestre;

    @Column(name="nota_teste1")
    private double notaTeste1;
    @Column(name="publicada_teste1")
    private boolean publicadaTeste1;
    @Column(name="graded_teste1")
    private boolean gradedTeste1;
    @Column(name="observacao_teste1")
    private String observacaoTeste1;

    @Column(name="nota_teste2")
    private double notaTeste2;
    @Column(name="publicada_teste2")
    private boolean publicadaTeste2;
    @Column(name="graded_teste2")
    private boolean gradedTeste2;
    @Column(name="observacao_teste2")
    private String observacaoTeste2;

    @Column(name="nota_trabalho")
    private double notaTrabalho;
    @Column(name="publicada_trabalho")
    private boolean publicadaTrabalho;
    @Column(name="graded_trabalho")
    private boolean gradedTrabalho;
    @Column(name="observacao_trabalho")
    private String observacaoTrabalho;

    @Column(name="nota_frequencia")
    private double notaFrequencia;
    @Column(name="publicada_frequencia")
    private boolean publicadaFrequencia;
    @Column(name="graded_nota_frequencia")
    private boolean gradedNotaFrequencia;
    @Column(name="resultado_frequencia")
    private String resultadoFrequencia;

    @Column(name="nota_exame_normal")
    private double notaExameNormal;
    @Column(name="publicado_exame_normal")
    private boolean publicadoExameNormal;
    @Column(name="graded_exame_normal")
    private boolean gradedExameNormal;
    @Column(name="observacao_exame_normal")
    private String observacaoExameNormal;

    @Column(name="nota_exame_recorrencia")
    private double notaExameRecorrencia;
    @Column(name="publicado_exame_recorrencia")
    private boolean publicadoExameRecorrencia;
    @Column(name="graded_exame_recorrencia")
    private boolean gradedExameRecorrencia;
    @Column(name="observacao_exame_recorrencia")
    private String obervacaoExameRecorrencia;

    @Column(name="nota_final")
    private double notaFinal;
    @Column(name="publicada_nota_final")
    private boolean publicadaNotaFinal;
    @Column(name="graded_nota_final")
    private boolean gradedNotaFinal;
    @Column(name="resultado_final")
    private String resultadoFinal;

    @Column(name="data_nota_frequencia")
    private Date dataNotaFrequencia;

}
