package siga.artsoft.api.viaingresso;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="viaingresso")
@Entity(name="ViaIngresso")
public class ViaIngresso extends IdEntity {

    private static final long serialVersionUID = 2747392265676106991L;

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
