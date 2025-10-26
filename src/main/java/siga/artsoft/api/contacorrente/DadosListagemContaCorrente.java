package siga.artsoft.api.contacorrente;

import java.util.Date;


public record DadosListagemContaCorrente(

        String descricao,
        String entidade,
        String referencia,
        double totalDebito,
        Date prazo,
        double credito,
        Date dataPagamento,
        boolean pago,
        String situacao,
        double taxa_desconto,
        Long id,
        Long recibo
) {

    public DadosListagemContaCorrente(ContaCorrente conta){
        this(
                conta.getTipoEmolumento(),
                "40202",
                conta.getReferencia(),
                conta.getTotalDebito().doubleValue(),
                conta.getDataLimitePagamento(),
                conta.getCredito().doubleValue(),
                conta.getDataPagamento(),
                conta.isPago(),
                conta.getSituacao(),
                conta.getTaxaDesconto(),
                conta.getId(),
                conta.getRecibo()
        );


    }

}
