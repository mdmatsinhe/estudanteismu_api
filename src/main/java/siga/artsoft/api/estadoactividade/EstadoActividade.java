package siga.artsoft.api.estadoactividade;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="estadoactividade")
@Entity(name="EstadoActividade")
public class EstadoActividade extends IdEntity {

    private static final long serialVersionUID = -5489170072387437142L;

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
