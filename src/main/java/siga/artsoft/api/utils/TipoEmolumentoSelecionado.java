package siga.artsoft.api.utils;

import siga.artsoft.api.emolumento.Emolumento;

public class TipoEmolumentoSelecionado {
    private Emolumento propina;
    private Emolumento taxaMatricula;
    private Emolumento taxaInscricao;
    private Emolumento taxaAtraso;
    private Emolumento propinaAvulsa;

    public TipoEmolumentoSelecionado() {
    }

    public Emolumento getPropina() {
        return this.propina;
    }

    public Emolumento getTaxaMatricula() {
        return this.taxaMatricula;
    }

    public Emolumento getTaxaInscricao() {
        return this.taxaInscricao;
    }

    public Emolumento getTaxaAtraso() {
        return this.taxaAtraso;
    }

    public Emolumento getPropinaAvulsa() {
        return this.propinaAvulsa;
    }

    public void setPropina(final Emolumento propina) {
        this.propina = propina;
    }

    public void setTaxaMatricula(final Emolumento taxaMatricula) {
        this.taxaMatricula = taxaMatricula;
    }

    public void setTaxaInscricao(final Emolumento taxaInscricao) {
        this.taxaInscricao = taxaInscricao;
    }

    public void setTaxaAtraso(final Emolumento taxaAtraso) {
        this.taxaAtraso = taxaAtraso;
    }

    public void setPropinaAvulsa(final Emolumento propinaAvulsa) {
        this.propinaAvulsa = propinaAvulsa;
    }
}
