package siga.artsoft.api.turno;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import siga.artsoft.api.utils.IdEntity;

@Table(name="turno")
@Entity(name="Turno")
public class Turno extends IdEntity {

    private static final long serialVersionUID = 7268423199348094165L;
    private String turno;

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
