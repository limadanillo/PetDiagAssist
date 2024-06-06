package br.com.petdiagassist.core.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public interface GeneticConstraint<A extends Annotation, T> extends ConstraintValidator<A, T> {
    boolean validating(ConstraintValidatorContext context, boolean validation, String msg);
}
