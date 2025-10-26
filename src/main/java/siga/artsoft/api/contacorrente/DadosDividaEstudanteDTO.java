package siga.artsoft.api.contacorrente;
import java.math.BigDecimal;

public class DadosDividaEstudanteDTO {
    private BigDecimal totalDivida;
    private boolean estudanteEstaEmDivida;

    public DadosDividaEstudanteDTO(BigDecimal totalDivida, boolean estudanteEstaEmDivida) {
        this.totalDivida = totalDivida;
        this.estudanteEstaEmDivida = estudanteEstaEmDivida;
    }

    public BigDecimal getTotalDivida() {
        return totalDivida;
    }

    public boolean isEstudanteEstaEmDivida() {
        return estudanteEstaEmDivida;
    }

    public void setTotalDivida(BigDecimal totalDivida) {
        this.totalDivida = totalDivida;
    }

    public void setEstudanteEstaEmDivida(boolean estudanteEstaEmDivida) {
        this.estudanteEstaEmDivida = estudanteEstaEmDivida;
    }
}

