package dev.sunrise.application.event_time;

import dev.sunrise.domain.City;
import org.springframework.stereotype.Service;

@Service
public interface EventTimeProvider {
    EventTimeResult getForCities(Iterable<City> cities);
}
