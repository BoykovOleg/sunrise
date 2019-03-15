package dev.sunrise.application;

import dev.sunrise.domain.City;
import dev.sunrise.domain.CityRepository;
import dev.sunrise.application.event_time.EventTimeProvider;
import dev.sunrise.application.event_time.EventTimeResultFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationEventTimeTest {

    @MockBean
    private CityRepository cityRepository;

    @MockBean
    private EventTimeProvider eventTimeProvider;

    @Autowired
    private Application application;

    @Before
    public void setUp() {
        Iterable<City> cities = Arrays.asList(
            new City("Zp", 1.0, 1.0),
            new City("Kv", 1.0, 2.0)
        );
        when(cityRepository.findAllByNameIn(anyIterable())).thenReturn(cities);
        when(eventTimeProvider.getForCities(cities)).thenReturn(EventTimeResultFixture.getFixtures());
    }

    @Test
    public void getEventTimeForCitiesSunriseSunsetTest() {
        GetCitiesEventTimeDTO dto = new GetCitiesEventTimeDTO(Arrays.asList("Zp", "Kv"), "sunrise_sunset");

        EventTimeResultDTO resultDTO = application.getEventTimeForCities(dto);
        assertThat(resultDTO.getResult()).allMatch(
            e -> e.getCity().getName().equals("Zp") &&
            e.getSunrise() != null &&
            e.getSunset() != null
        );
        assertThat(resultDTO.getErrors()).allMatch(
            e -> e.getCity().getName().equals("Kv") &&
            !e.getError().isEmpty()
        );
    }

    @Test
    public void getEventTimeForCitiesSunriseTest() {
        GetCitiesEventTimeDTO dto = new GetCitiesEventTimeDTO(Arrays.asList("Zp", "Kv"), "sunrise");

        EventTimeResultDTO resultDTO = application.getEventTimeForCities(dto);
        assertThat(resultDTO.getResult()).allMatch(
                e -> e.getCity().getName().equals("Zp") &&
                        e.getSunrise() != null &&
                        e.getSunset() == null
        );
        assertThat(resultDTO.getErrors()).allMatch(
                e -> e.getCity().getName().equals("Kv") &&
                        !e.getError().isEmpty()
        );
    }

    @Test
    public void getEventTimeForCitiesSunsetTest() {
        GetCitiesEventTimeDTO dto = new GetCitiesEventTimeDTO(Arrays.asList("Zp", "Kv"), "sunset");

        EventTimeResultDTO resultDTO = application.getEventTimeForCities(dto);
        assertThat(resultDTO.getResult()).allMatch(
                e -> e.getCity().getName().equals("Zp") &&
                        e.getSunrise() == null &&
                        e.getSunset() != null
        );
        assertThat(resultDTO.getErrors()).allMatch(
                e -> e.getCity().getName().equals("Kv") &&
                        !e.getError().isEmpty()
        );
    }
}
