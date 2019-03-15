package dev.sunrise.application.event_time.sunrise_api;

import java.time.ZonedDateTime;

public class ResultDTO {
    private ZonedDateTime sunrise;
    private ZonedDateTime sunset;
    private String error;

    private ResultDTO() {}

    static ResultDTO createWithError(String error) {
        ResultDTO dto = new ResultDTO();
        dto.error = error;

        return dto;
    }

    static ResultDTO createWithResponseResult(ResponseResult result) {
        ResultDTO dto = new ResultDTO();
        dto.sunrise = result.sunrise;
        dto.sunset = result.sunset;

        return dto;
    }

    public boolean hasError() {
        return error != null;
    }

    public String getError() {
        return error;
    }

    public ZonedDateTime getSunrise() {
        return sunrise;
    }

    public ZonedDateTime getSunset() {
        return sunset;
    }
}
