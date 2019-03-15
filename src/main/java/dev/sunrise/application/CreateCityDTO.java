package dev.sunrise.application;

import dev.sunrise.domain.City;
import dev.sunrise.application.validators.UniqueGeoCoordinates;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@UniqueGeoCoordinates
public class CreateCityDTO {
    @NotNull
    @Size(min = 2, max = 100)
    private final String name;

    private final double longitude;

    private final double latitude;

    CreateCityDTO(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    City toCityModel() {
        return new City(name, latitude, longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
