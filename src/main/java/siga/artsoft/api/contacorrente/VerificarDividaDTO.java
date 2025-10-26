package siga.artsoft.api.contacorrente;

import jakarta.validation.constraints.NotNull;

public class VerificarDividaDTO {

    @NotNull(message = "O ID do estudante não pode ser nulo.")
    private Long idEstudante;

    public VerificarDividaDTO() {
    }

    public VerificarDividaDTO(Long idEstudante) {
        this.idEstudante = idEstudante;
    }

    public Long getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(Long idEstudante) {
        this.idEstudante = idEstudante;
    }
}
