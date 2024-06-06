package br.com.petdiagassist.core.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValidEnumValue, String> {
    private Class<? extends Enum<?>> enumSelected;

    @Override
    public void initialize(ValidEnumValue constraintAnnotation) {
        enumSelected = constraintAnnotation.targetClassType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }

        for (Enum<?> enumVal : enumSelected.getEnumConstants()) {
            if (enumVal.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
