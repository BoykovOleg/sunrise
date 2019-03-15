package dev.sunrise.application;

import dev.sunrise.domain.City;

import javax.validation.ConstraintViolation;
import java.util.Set;

public final class CityCreationResult {
    private final Set<ConstraintViolation<CreateCityDTO>> errors;
    private final City city;

    CityCreationResult(City city) {
        this.city = city;
        this.errors = null;
    }

    CityCreationResult(Set<ConstraintViolation<CreateCityDTO>> errors) {
        this.errors = errors;
        this.city = null;
    }

    public boolean isValid() {
        return errors == null || errors.isEmpty();
    }

    public Set<ConstraintViolation<CreateCityDTO>> getErrors() {
        return errors;
    }

    public City getCity() {
        return city;
    }
}
