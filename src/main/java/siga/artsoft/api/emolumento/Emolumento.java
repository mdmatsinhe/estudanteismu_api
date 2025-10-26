package siga.artsoft.api.emolumento;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import siga.artsoft.api.tipoemolumento.TipoEmolumento;
import siga.artsoft.api.utils.IdEntity;

import java.math.BigDecimal;

@Table(name="emolumento")
@Entity(name="Emolumento")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Emolumento extends IdEntity {

    @ManyToOne
    @JoinColumn(name = "tipoemloumento_id")
    private TipoEmolumento tipoEmolumento;

    private BigDecimal valor;

}
