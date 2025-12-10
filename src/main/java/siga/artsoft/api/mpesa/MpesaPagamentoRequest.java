package siga.artsoft.api.mpesa;

import lombok.Data;

@Data
public class MpesaPagamentoRequest {
    private Long contaId;
    private String msisdn;
}
