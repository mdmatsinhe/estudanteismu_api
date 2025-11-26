package siga.artsoft.api.candidatura;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MozaCamposValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMozaCampos {
    String message() default "Para candidatos moçambicanos, província e distrito são obrigatórios.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
