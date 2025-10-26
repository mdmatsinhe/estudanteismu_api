package siga.artsoft.api.tipoemolumento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import siga.artsoft.api.utils.IdEntity;

@Table(name="tipoemolumento")
@Entity(name="TipoEmolumento")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TipoEmolumento extends IdEntity {
    private static final long serialVersionUID = 6486628929373905369L;

    @Column(name = "tipo_emolumento")
    private String tipoEmolumento;
    @Column(unique=true)
    private String abreviatura;
}
