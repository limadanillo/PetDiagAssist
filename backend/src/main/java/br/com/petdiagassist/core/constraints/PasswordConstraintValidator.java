package br.com.petdiagassist.core.constraints;

import br.com.petdiagassist.utils.StringUtils;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link PasswordConstraint} to be able to enable custom validations
 *
 * @author Danillo Lima
 * @since 06/20/2021
 */
public class PasswordConstraintValidator implements GeneticConstraint<PasswordConstraint, String> {

    private static final String REGEX_VALID_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$";// Minimo 8 caracteres, 1 letra maiuscula, 1 letra minuscula, 1 numero e 1 caracter especial
    private boolean REQUIRE;

    @Override
    public void initialize(PasswordConstraint constrain) {
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (REQUIRE && Boolean.TRUE.equals(StringUtils.isNullOrBlank(value))) {
            return validating(context, true, "{message.error.password.not.blank}");
        } else if (Boolean.TRUE.equals(StringUtils.isNotNullAndBlank(value)) && !value.matches(REGEX_VALID_PASSWORD)) {
            return validating(context, true, "{message.error.password.pattern}");
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

