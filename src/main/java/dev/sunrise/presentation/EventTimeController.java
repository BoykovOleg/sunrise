package dev.sunrise.presentation;

import dev.sunrise.application.Application;
import dev.sunrise.application.EventTimeResultDTO;
import dev.sunrise.application.GetCitiesEventTimeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventTimeController {

    private Application application;

    public EventTimeController(Application application) {
        this.application = application;
    }

    @GetMapping(path = "/event_time")
    public EventTimeResultDTO getCities(
            @RequestParam(name = "type", defaultValue = "sunrise_sunset") String type,
            @RequestParam(name = "cities") List<String> cities
    ) {
        return application.getEventTimeForCities(new GetCitiesEventTimeDTO(cities, type));
    }
}
