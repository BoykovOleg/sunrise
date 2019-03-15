package dev.sunrise.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationEventTimeValidationTest {

    @Autowired
    private Application application;

    @Test
    public void getEventTimeForCitiesEmptyCities() {
        GetCitiesEventTimeDTO dto = new GetCitiesEventTimeDTO(new ArrayList<>(), "sunrise_sunset");
        try {
            application.getEventTimeForCities(dto);
        } catch (ConstraintViolationException e) {
            assertThat(e.getConstraintViolations()).isNotEmpty().allMatch(
                v -> v.getMessageTemplate().equals("{javax.validation.constraints.NotEmpty.message}") &&
                v.getPropertyPath().toString().equals("cities")
            );
        }
    }

    @Test
    public void getEventTimeForCitiesWrongType() {
        GetCitiesEventTimeDTO dto = new GetCitiesEventTimeDTO(Arrays.asList("Zp", "Kv"), "wrong");
        try {
            application.getEventTimeForCities(dto);
        } catch (ConstraintViolationException e) {
            assertThat(e.getConstraintViolations()).isNotEmpty().allMatch(
                v -> v.getMessageTemplate().equals("{dev.sunrise.application.validators.ValidEventType}") &&
                v.getPropertyPath().toString().equals("type")
            );
        }
    }
}
