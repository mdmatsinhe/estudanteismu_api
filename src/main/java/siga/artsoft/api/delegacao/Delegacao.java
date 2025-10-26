package siga.artsoft.api.delegacao;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="delegacao")
@Entity(name="Delegacao")
public class Delegacao extends IdEntity {

    private String nome;
    private String endereco;
    private int contacto_fixo;
    private int contacto_movel;
}
