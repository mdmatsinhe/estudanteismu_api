package siga.artsoft.api.semestreano;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="semestreano")
@Entity(name="SemestreAno")
public class SemestreAno extends IdEntity {

    private static final long serialVersionUID = -3622665955053202469L;
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
