package siga.artsoft.api.tipodocumentoidentificacao;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="tipodocumentoidentificacao")
@Entity(name="TipoDocumentoIdentificacao")
public class TipoDocumentoIdentificacao extends IdEntity {

    private static final long serialVersionUID = 2747392265676106991L;

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
