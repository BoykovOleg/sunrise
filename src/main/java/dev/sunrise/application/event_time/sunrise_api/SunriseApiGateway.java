package dev.sunrise.application.event_time.sunrise_api;

import dev.sunrise.domain.GeoCoordinates;

public interface SunriseApiGateway {
    ResultDTO get(GeoCoordinates coordinates);
}
