package siga.artsoft.api.curriculo;

import jakarta.persistence.*;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.planoestudos.PlanoEstudos;
import siga.artsoft.api.precedencia.Precedencia;
import siga.artsoft.api.utils.IdEntity;

import java.util.ArrayList;
import java.util.List;

@Table(name="curriculo")
@Entity(name="Curriculo")
//@Getter
//@AllArgsConstructor
public class Curriculo extends IdEntity {

    private static final long serialVersionUID = 7947558894418380600L;
    private int ano_inicio;
    private int ano_fim;
    private String descricao;
    private boolean activo;

    @OneToMany(mappedBy="curriculo", cascade= CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    List<PlanoEstudos> planoEstudos=new ArrayList<PlanoEstudos>();

    public int getAno_inicio() {
        return ano_inicio;
    }

    public void setAno_inicio(int ano_inicio) {
        this.ano_inicio = ano_inicio;
    }

    public int getAno_fim() {
        return ano_fim;
    }

    public void setAno_fim(int ano_fim) {
        this.ano_fim = ano_fim;
    }

    public List<PlanoEstudos> getPlanoEstudos() {
        return planoEstudos;
    }

    public void setPlanoEstudos(List<PlanoEstudos> planoEstudos) {
        this.planoEstudos = planoEstudos;
    }

    public List<Precedencia> getPrecedencias() {
        return precedencias;
    }

    public void setPrecedencias(List<Precedencia> precedencias) {
        this.precedencias = precedencias;
    }

    @OneToMany(mappedBy="curriculo", cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    List<Precedencia> precedencias=new ArrayList<Precedencia>();

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public int getAnoInicio() {
        return ano_inicio;
    }

    public void setAnoInicio(int anoInicio) {
        this.ano_inicio = anoInicio;
    }

    public int getAnoFim() {
        return ano_fim;
    }

    public void setAnoFim(int anoFim) {
        this.ano_fim = anoFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

//    public void addPlanoEstudos(DisciplinaSemestre disciplina) {
//        disciplina.setCurriculo(this);
//        planoEstudos.add(disciplina);
//    }

    public void addPrecedencia(Precedencia precedencia) {
        precedencia.setCurriculo(this);
        precedencias.add(precedencia);
    }
}
