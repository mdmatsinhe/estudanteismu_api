package siga.artsoft.api.distrito;

import jakarta.persistence.*;
import siga.artsoft.api.provincia.Provincia;
import siga.artsoft.api.utils.IdEntity;

@Table(name="distrito")
@Entity(name="Distrito")
public class Distrito extends IdEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    private String nome;

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
