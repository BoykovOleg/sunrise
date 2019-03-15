package dev.sunrise.domain;

import java.time.ZonedDateTime;

public class EventTime {
    private City city;
    private ZonedDateTime sunrise;
    private ZonedDateTime sunset;

    public EventTime(City city, ZonedDateTime sunrise, ZonedDateTime sunset) {
        this.city = city;
        this.sunrise = sunrise;
        this.sunset = sunset;
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
