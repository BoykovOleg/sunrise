package dev.sunrise.application;

import dev.sunrise.domain.EventType;
import dev.sunrise.application.validators.ValidEventType;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class GetCitiesEventTimeDTO {
    @NotEmpty
    private final List<String> cities;

    @ValidEventType
    private final String type;

    public GetCitiesEventTimeDTO(@NotEmpty List<String> cities, String type) {
        this.cities = cities;
        this.type = type;
    }

    EventType getEventType() {
        return EventType.valueOf(type.toUpperCase());
    }

    List<String> getCities() {
        return cities;
    }
}

