package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<ValidationDate, LocalDate> {

	private LocalDate minDate;

	@Override
	public void initialize(ValidationDate constraintAnnotation) {
		minDate = LocalDate.parse(constraintAnnotation.minDate());
	}

	@Override
	public boolean isValid(LocalDate inputDate, ConstraintValidatorContext context) {
		System.out.println(context.getDefaultConstraintMessageTemplate());
		return inputDate.isAfter(minDate);
	}
}