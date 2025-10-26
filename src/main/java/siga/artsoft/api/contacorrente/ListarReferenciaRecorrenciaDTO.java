package siga.artsoft.api.contacorrente;

import java.math.BigDecimal;
import java.util.Date;

public class ListarReferenciaRecorrenciaDTO {

    private Long id;
    private BigDecimal debito;
    private BigDecimal totalDebito;
    private String tipoEmolumento;
    private String situacao;
    private String referencia;
    private Date prazo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getTotalDebito() {
        return totalDebito;
    }

    public void setTotalDebito(BigDecimal totalDebito) {
        this.totalDebito = totalDebito;
    }

    public String getTipoEmolumento() {
        return tipoEmolumento;
    }

    public void setTipoEmolumento(String tipoEmolumento) {
        this.tipoEmolumento = tipoEmolumento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }
}
