package br.com.petdiagassist.core.constraints;

import br.com.petdiagassist.utils.StringUtils;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link RoleConstraint} to be able to enable custom validations
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
public class RoleConstraintValidator implements GeneticConstraint<RoleConstraint, String> {

    private static final String ROLE_FORMAT = "^ADMIN|USER|LEADER|INTERN$";
    private boolean REQUIRE;

    @Override
    public void initialize(RoleConstraint constrain) {
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, REQUIRE && StringUtils.isNullOrBlank(value), "{message.error.role.not.blank}")) return false;
        else if(StringUtils.isNotNullAndBlank(value)) {
            if (validating(context, value.trim().length() > 63, "{message.error.role.max.size}")) return false;
            if (validating(context, !value.matches(ROLE_FORMAT), "{message.error.role.pattern}")) return false;
        }
        return true;
    }

    public boolean validating(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return true;
        }
        return false;
    }
}

