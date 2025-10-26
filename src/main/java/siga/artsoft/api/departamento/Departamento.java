package siga.artsoft.api.departamento;

import jakarta.persistence.*;
import siga.artsoft.api.faculdade.Faculdade;
import siga.artsoft.api.utils.IdEntity;

@Table(name="departamento")
@Entity(name="Departamento")
public class Departamento extends IdEntity {

    private String nome;
    private String nome_abreviado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculdade")
    private Faculdade faculdade;
}
