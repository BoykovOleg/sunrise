package dev.sunrise.application.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueGeoCoordinatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGeoCoordinates {
    String message() default "{dev.sunrise.application.validators.UniqueGeoCoordinates}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
