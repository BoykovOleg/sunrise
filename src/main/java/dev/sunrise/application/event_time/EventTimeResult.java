package dev.sunrise.application.event_time;

import dev.sunrise.domain.City;
import dev.sunrise.domain.EventTime;
import dev.sunrise.application.event_time.sunrise_api.ResultDTO;

import java.util.ArrayList;
import java.util.List;

public class EventTimeResult {
    private List<EventTime> eventTimes = new ArrayList<>();
    private List<EventTimeError> errors = new ArrayList<>();

    public class EventTimeError {
        private City city;
        private String error;

        private EventTimeError(City city, String error) {
            this.city = city;
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public City getCity() {
            return city;
        }
    }

    void addResultForCity(ResultDTO result, City city) {
        if (result.hasError()) {
            errors.add(new EventTimeError(city, result.getError()));
        } else {
            eventTimes.add(new EventTime(city, result.getSunrise(), result.getSunset()));
        }

    }

    public List<EventTime> getEventTimes() {
        return eventTimes;
    }

    public List<EventTimeError> getErrors() {
        return errors;
    }
}
