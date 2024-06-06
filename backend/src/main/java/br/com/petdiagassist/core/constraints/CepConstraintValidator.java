package br.com.petdiagassist.core.constraints;

import br.com.petdiagassist.utils.StringUtils;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link CepConstraint} to be able to enable custom validations
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
public class CepConstraintValidator implements GeneticConstraint<CepConstraint, String> {

    private static final String CEP_FORMAT = "^\\d{5}-\\d{3}$";
    private boolean REQUIRE;

    @Override
    public void initialize(CepConstraint constrain) {
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, (REQUIRE && StringUtils.isNullOrBlank(value)), "{message.error.cep.not.blank}")) return false;
        else if(StringUtils.isNotNullAndBlank(value)) {
            if (validating(context, value.trim().length() != 9, "{message.error.cep.size}")) return false;
            if (validating(context, !value.matches(CEP_FORMAT), "{message.error.cep.pattern}")) return false;
        }
        return true;
    }

    @Override
    public boolean validating(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return true;
        }
        return false;
    }
}

