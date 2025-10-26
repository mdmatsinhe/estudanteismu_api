package siga.artsoft.api.nacionalidade;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="nacionalidade")
@Entity(name="Nacionalidade")
public class Nacionalidade extends IdEntity {

    private static final long serialVersionUID = -5489170072387437142L;

    private String nacionalidade;

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}
