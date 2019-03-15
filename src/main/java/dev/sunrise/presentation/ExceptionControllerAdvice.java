package dev.sunrise.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity handleValidationErrors(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getConstraintViolations().stream().map(ErrorResponse::fromViolation));
    }
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityException(DataIntegrityViolationException ex) {

        logger.warn("Data integrity violation", ex);
        // we can add custom error
        return ResponseEntity.unprocessableEntity().body(ErrorResponse.createWithOnlyMessage("Oops something went wrong"));
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception ex) {
        logger.error("Caught exception", ex);
        return ResponseEntity.unprocessableEntity().body(ErrorResponse.createWithOnlyMessage("Oops something went wrong"));
    }
}
@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorResponse {
    private final String field;
    private final String message;

    private ErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    static ErrorResponse createWithOnlyMessage(String message) {
        return new ErrorResponse(null, message);
    }

    static ErrorResponse fromViolation(ConstraintViolation violation) {
        return new ErrorResponse(violation.getPropertyPath().toString(), violation.getMessage());
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
