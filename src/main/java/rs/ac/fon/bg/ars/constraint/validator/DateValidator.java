package rs.ac.fon.bg.ars.constraint.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import rs.ac.fon.bg.ars.constraint.DateConstraint;
import rs.ac.fon.bg.ars.model.Price;

public class DateValidator implements ConstraintValidator<DateConstraint, Price> {
    @Override
    public void initialize(DateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Price price, ConstraintValidatorContext constraintValidatorContext) {
        return (price.getDateTo().isAfter(price.getDateFrom()));
    }
}
