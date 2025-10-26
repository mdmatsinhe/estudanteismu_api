package siga.artsoft.api.antecedenteescolar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.utils.IdEntity;

@Table(name="antecedenteescolar")
@Entity(name="AntecedenteEscolar")
public class AntecedenteEscolar extends IdEntity {

    private static final long serialVersionUID = 8741355248630718268L;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudante_id")
    @JsonIgnore
    private Estudante estudante;
    private String instituicao;
    private String local;
    private String duracao;
    private String grau;
    private String ano_conclusao;


    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String intituicao) {
        instituicao = intituicao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public String getAno_conclusao() {
        return ano_conclusao;
    }

    public void setAno_conclusao(String ano_conclusao) {
        this.ano_conclusao = ano_conclusao;
    }
}
