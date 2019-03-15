package dev.sunrise.application.validators;

import dev.sunrise.domain.City;
import dev.sunrise.domain.CityRepository;
import dev.sunrise.domain.GeoCoordinates;
import dev.sunrise.application.CreateCityDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueGeoCoordinatesValidator implements ConstraintValidator<UniqueGeoCoordinates, CreateCityDTO> {

    private CityRepository repository;

    public UniqueGeoCoordinatesValidator(CityRepository repository) {
        this.repository = repository;
    }

    public void initialize(UniqueGeoCoordinates constraint) {
    }

    public boolean isValid(CreateCityDTO obj, ConstraintValidatorContext context) {
        City city = repository.findCityByCoordinates(new GeoCoordinates(obj.getLatitude(), obj.getLongitude()));

        return city == null;
    }
}
