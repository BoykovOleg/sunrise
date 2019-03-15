package dev.sunrise.application.validators;

import dev.sunrise.domain.EventType;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEventTypeValidator implements ConstraintValidator<ValidEventType, String> {
   public void initialize(ValidEventType constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
       return EnumUtils.isValidEnumIgnoreCase(EventType.class, obj);
   }
}
