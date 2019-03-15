package dev.sunrise.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.sunrise.domain.City;
import dev.sunrise.domain.EventTime;
import dev.sunrise.domain.EventType;
import dev.sunrise.application.event_time.EventTimeResult;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventTimeResultDTO {
    private List<EventTimeDTO> result;
    private List<EventTimeResult.EventTimeError> errors;

    private EventTimeResultDTO() {}

    static EventTimeResultDTO from(EventTimeResult result, EventType type){
        EventTimeResultDTO dto = new EventTimeResultDTO();
        dto.result = result.getEventTimes().stream().map(e -> new EventTimeDTO(e, type)).collect(Collectors.toList());
        dto.errors = new ArrayList<>(result.getErrors());

        return dto;
    }

     @JsonInclude(JsonInclude.Include.NON_NULL)
     static class EventTimeDTO  {
        private City city;
        private ZonedDateTime sunrise;
        private ZonedDateTime sunset;

        private EventTimeDTO(EventTime eventTime, EventType type) {
            this.city = eventTime.getCity();
            switch (type) {
                case SUNRISE:
                    this.sunrise = eventTime.getSunrise();
                    this.sunset = null;
                    break;
                case SUNSET:
                    this.sunset = eventTime.getSunset();
                    this.sunrise = null;
                    break;
                case SUNRISE_SUNSET:
                    this.sunrise = eventTime.getSunrise();
                    this.sunset = eventTime.getSunset();
                    break;
            }
        }

        public City getCity() {
            return city;
        }

        public ZonedDateTime getSunrise() {
            return sunrise;
        }

        public ZonedDateTime getSunset() {
            return sunset;
        }
    }

    public List<EventTimeResult.EventTimeError> getErrors() {
        return errors;
    }

    public List<EventTimeDTO> getResult() {
        return result;
    }
}
