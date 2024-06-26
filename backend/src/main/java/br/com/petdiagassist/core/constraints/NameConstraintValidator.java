package br.com.petdiagassist.core.constraints;

import br.com.petdiagassist.utils.StringUtils;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * Constraint Validator created to handle annotations of type {@link NameConstraint} to be able to enable custom validations
 *
 * @author Danillo Lima
 * @since 06/19/2021
 */
public class NameConstraintValidator implements GeneticConstraint<NameConstraint, String> {

    @Autowired
    private MessageSource messageSource;

    private String PERSON = null;
    private String FIELD = null;
    private boolean REQUIRE;
    private static final String REGEX_NOT_NUMBER = "^[^0-9]+$";
    private static final String REGEX_VALID_NAME = "^([\\w'\\-,.][^0-9_!¡?÷?¿/\\\\\\\\+=@#$%ˆ&*()\\\\{}|~<>;:[\\\\]]{2,})$";

    @Override
    public void initialize(NameConstraint constrain) {
        PERSON = constrain.person();
        FIELD = constrain.field();
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, FIELD == null, "message.error.name.default")) return false;
        if (validating(context, REQUIRE && StringUtils.isNullOrBlank(value), "message.error." + FIELD + ".name.not.blank")) return false;
        else if(StringUtils.isNotNullAndBlank(value)) {
            if (validating(context, !(value.trim().length() <= 63), "message.error." + FIELD + ".name.max.size")) return false;
            if (validating(context, !value.matches(REGEX_NOT_NUMBER), "message.error." + FIELD + ".name.pattern.not.number")) return false;
            if (validating(context, !value.matches(REGEX_VALID_NAME), "message.error." + FIELD + ".name.pattern")) return false;
        }
        return true;
    }

    public boolean validating(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
            hibernateContext.disableDefaultConstraintViolation();
            hibernateContext.addExpressionVariable("person", PERSON)
                    .buildConstraintViolationWithTemplate(messageSource.getMessage(msg, null, Locale.getDefault()))
                    .addConstraintViolation();
            return true;
        }
        return false;
    }
}
