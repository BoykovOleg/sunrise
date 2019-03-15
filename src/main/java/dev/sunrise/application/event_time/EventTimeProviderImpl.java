package dev.sunrise.application.event_time;

import dev.sunrise.domain.City;
import dev.sunrise.application.event_time.sunrise_api.ResultDTO;
import dev.sunrise.application.event_time.sunrise_api.SunriseApiGateway;
import org.springframework.stereotype.Service;

@Service
public class EventTimeProviderImpl implements EventTimeProvider {

    private SunriseApiGateway apiGateway;

    public EventTimeProviderImpl(SunriseApiGateway apiGateway) {
        this.apiGateway = apiGateway;
    }

    @Override
    public EventTimeResult getForCities(Iterable<City> cities) {
        EventTimeResult eventTimeResult = new EventTimeResult();
        for (City city: cities) {
            ResultDTO result = apiGateway.get(city.getCoordinates());
            eventTimeResult.addResultForCity(result, city);
        }
        return eventTimeResult;
    }
}
