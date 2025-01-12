package bstefanov.transportOrg.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Pattern(regexp = "^([0-9]+.[0-9]+)( ?),( ?)([0-9]+.[0-9]+)$", message = "The coordinates are invalid!")
@Constraint(validatedBy = {})
public @interface Coordinates {
    String message() default "The coordinates are invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
