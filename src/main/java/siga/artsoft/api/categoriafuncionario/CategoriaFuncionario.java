package siga.artsoft.api.categoriafuncionario;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="categoriafuncionario")
@Entity(name="CategoriaFuncionario")
public class CategoriaFuncionario extends IdEntity {

    private static final long serialVersionUID = 2747392265676106991L;

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
