package siga.artsoft.api.estadocivil;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="estadocivil")
@Entity(name="EstadoCivil")
public class EstadoCivil extends IdEntity {

    private static final long serialVersionUID = -5489170072387437142L;

    private String estado_civil;

    public String getEstadoCivil() {
        return estado_civil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estado_civil = estadoCivil;
    }

}
