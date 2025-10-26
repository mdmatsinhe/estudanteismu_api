package siga.artsoft.api.precedencia;

import jakarta.persistence.*;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.disciplina.Disciplina;
import siga.artsoft.api.utils.IdEntity;

@Table(name="precedencia")
@Entity(name="Precedencia")
public class Precedencia extends IdEntity {

    private static final long serialVersionUID = -2792033445676533718L;

    @ManyToOne
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    @ManyToOne
    @JoinColumn(name = "disciplinasucessede_id")
    private Disciplina disciplinaSucessede;

    @ManyToOne
    @JoinColumn(name = "disciplinaprecede_id")
    private Disciplina disciplinaPrecede;


    @Column(name="semestre_diciplina_succede")
    private int semestreDiciplinaSuccede;
    @Column(name="semestre_disciplina_precede")
    private int semestreDisciplinaPrecede;

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }

    public Disciplina getDisciplinaSucessede() {
        return disciplinaSucessede;
    }

    public void setDisciplinaSucessede(Disciplina disciplinaSucessede) {
        this.disciplinaSucessede = disciplinaSucessede;
    }

    public Disciplina getDisciplinaPrecede() {
        return disciplinaPrecede;
    }

    public void setDisciplinaPrecede(Disciplina disciplinaPrecede) {
        this.disciplinaPrecede = disciplinaPrecede;
    }

    public int getSemestreDiciplinaSuccede() {
        return semestreDiciplinaSuccede;
    }

    public void setSemestreDiciplinaSuccede(int semestreDiciplinaSuccede) {
        this.semestreDiciplinaSuccede = semestreDiciplinaSuccede;
    }

    public int getSemestreDisciplinaPrecede() {
        return semestreDisciplinaPrecede;
    }

    public void setSemestreDisciplinaPrecede(int semestreDisciplinaPrecede) {
        this.semestreDisciplinaPrecede = semestreDisciplinaPrecede;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((disciplinaPrecede == null) ? 0 : disciplinaPrecede
                .hashCode());
        result = prime
                * result
                + ((disciplinaSucessede == null) ? 0 : disciplinaSucessede
                .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Precedencia other = (Precedencia) obj;
        if (disciplinaPrecede == null) {
            if (other.disciplinaPrecede != null)
                return false;
        } else if (!disciplinaPrecede.equals(other.disciplinaPrecede))
            return false;
        if (disciplinaSucessede == null) {
            if (other.disciplinaSucessede != null)
                return false;
        } else if (!disciplinaSucessede.equals(other.disciplinaSucessede))
            return false;
        return true;
    }
}
