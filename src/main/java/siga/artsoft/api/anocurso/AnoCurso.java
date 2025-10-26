package siga.artsoft.api.anocurso;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="anocurso")
@Entity(name="AnoCurso")
public class AnoCurso extends IdEntity {

    private static final long serialVersionUID = -5489170072387437142L;
    private int codigo;
    private String descricao;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
