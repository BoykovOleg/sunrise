package dev.sunrise.application.event_time.sunrise_api;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SunriseApiResultDTOFixture {
    public static List<ResultDTO> getFixtures() {
        List<ResultDTO> list = new ArrayList<>();
        list.add(ResultDTO.createWithResponseResult(new ResponseResult(ZonedDateTime.now(), ZonedDateTime.now())));
        list.add(ResultDTO.createWithError("Something went wrong"));

        return list;
    }
}
