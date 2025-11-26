package siga.artsoft.api.candidatura;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MozaCamposValidator implements ConstraintValidator<ValidMozaCampos, CandidaturaDTO> {

    @Override
    public boolean isValid(CandidaturaDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true;
        boolean isMoza = dto.getNacionalidadeId() != null && dto.getNacionalidadeId() == 1L;

        if (isMoza) {
            boolean provinciaOk = dto.getProvinciaId() != null;
            boolean distritoOk = dto.getDistritoId() != null;

            if (!provinciaOk || !distritoOk) {
                return false;
            }
        }

        return true;
    }
}
