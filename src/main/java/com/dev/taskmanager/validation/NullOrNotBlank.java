package com.dev.taskmanager.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented 
@Constraint(validatedBy = { NullOrNotBlankValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNotBlank {

    String message() default "Non-Null value cannot be blank";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
