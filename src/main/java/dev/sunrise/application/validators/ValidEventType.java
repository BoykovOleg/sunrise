package dev.sunrise.application.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidEventTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEventType {
    String message() default "{dev.sunrise.application.validators.ValidEventType}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
