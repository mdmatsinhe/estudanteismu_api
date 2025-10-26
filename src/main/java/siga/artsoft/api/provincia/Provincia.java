package siga.artsoft.api.provincia;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="provincia")
@Entity(name="Provincia")
public class Provincia extends IdEntity {

    private static final long serialVersionUID = 1L;

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
