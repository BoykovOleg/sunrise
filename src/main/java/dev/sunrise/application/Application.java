package dev.sunrise.application;

import dev.sunrise.domain.City;
import dev.sunrise.domain.CityRepository;
import dev.sunrise.application.event_time.EventTimeProvider;
import dev.sunrise.application.event_time.EventTimeResult;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
public class Application {

    final private CityRepository cityRepository;

    final private Validator validator;

    final private EventTimeProvider eventTimeProvider;

    public Application(CityRepository cityRepository, Validator validator, EventTimeProvider eventTimeProvider) {
        this.cityRepository = cityRepository;
        this.validator = validator;
        this.eventTimeProvider = eventTimeProvider;
    }

    public SearchResult<City> getAllCities(Pageable pageable) {
        return SearchResult.createFromPage(cityRepository.findAll(pageable));
    }

    public CityCreationResult createCity(CreateCityDTO dto) {
        Set<ConstraintViolation<CreateCityDTO>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            return new CityCreationResult(errors);
        }

        return new CityCreationResult(cityRepository.save(dto.toCityModel()));
    }

    /**
     * There is two very similar classes: EventTimeResult and EventTimeResultDTO.
     * They are both DTO and are both used between packages layers.
     * The event_time package return EventTimeResult without any EventType filtration.
     * Entity EventTime always has sunset and sunrise, none of them could be null.
     * And only on the top level(this class) we filter EventTime's sunrise and sunset fields with EventType.
     * EventTimeResultDTO is the result of such filtering.
     */
    public EventTimeResultDTO getEventTimeForCities(GetCitiesEventTimeDTO dto) {
        Set<ConstraintViolation<GetCitiesEventTimeDTO>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
        Iterable<City> cities = cityRepository.findAllByNameIn(dto.getCities());
        EventTimeResult result = this.eventTimeProvider.getForCities(cities);

        return EventTimeResultDTO.from(result, dto.getEventType());
    }
}
