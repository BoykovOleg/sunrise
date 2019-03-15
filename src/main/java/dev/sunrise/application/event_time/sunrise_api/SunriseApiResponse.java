package dev.sunrise.application.event_time.sunrise_api;

import java.time.ZonedDateTime;

class SunriseApiResponse {
    private final String status;
    final ResponseResult results;

    public SunriseApiResponse(String status, ResponseResult results) {
        this.status = status;
        this.results = results;
    }

    boolean isOk() {
        return status != null && status.equals("OK");
    }
}

class ResponseResult {
    final ZonedDateTime sunrise;
    final ZonedDateTime sunset;

    ResponseResult(ZonedDateTime sunrise, ZonedDateTime sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
