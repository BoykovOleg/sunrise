package dev.sunrise.application.event_time;

import dev.sunrise.domain.City;
import dev.sunrise.application.event_time.sunrise_api.ResultDTO;
import dev.sunrise.application.event_time.sunrise_api.SunriseApiResultDTOFixture;

import java.util.List;

public class EventTimeResultFixture {
    public static EventTimeResult getFixtures() {
        EventTimeResult result = new EventTimeResult();

        List<ResultDTO> resultDTOList = SunriseApiResultDTOFixture.getFixtures();
        result.addResultForCity(resultDTOList.get(0), new City("Zp", 1.0f, 1.0f));
        result.addResultForCity(resultDTOList.get(1), new City("Kv", 1.0f, 2.0f));

        return result;
    }
}
