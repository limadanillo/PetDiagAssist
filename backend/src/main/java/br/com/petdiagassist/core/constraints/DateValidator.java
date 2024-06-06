package br.com.petdiagassist.core.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    private boolean future;
    private String customMessage;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        future = constraintAnnotation.future();
        customMessage = constraintAnnotation.customMessage();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }

        try {
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            boolean valid = future ? date.isAfter(LocalDate.now()) : !date.isAfter(LocalDate.now());

            if (!valid && !customMessage.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            }

            return valid;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}