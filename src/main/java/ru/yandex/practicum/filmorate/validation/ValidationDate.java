package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationDate {
	String message() default "Invalid date format. Required format: {format}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String minDate() default "1895-12-28";

	String format() default "yyyy-MM-dd";
}
