package dev.sunrise.presentation;

import dev.sunrise.application.Application;
import dev.sunrise.application.CityCreationResult;
import dev.sunrise.application.CreateCityDTO;
import dev.sunrise.domain.City;
import dev.sunrise.application.SearchResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
public class CityController {

    private Application application;

    public CityController(Application application) {
        this.application = application;
    }

    @GetMapping(path = "/cities")
    public SearchResult<City> getCities(
        @RequestParam(name = "page", defaultValue = "0") String page,
        @RequestParam(name = "perPage", defaultValue = "10") String perPage
    ) {
        return application.getAllCities(
            PageRequest.of(
                NumberUtils.toInt(page, 0),
                NumberUtils.toInt(perPage, 10),
                Sort.by("name"))
        );
    }

    @PostMapping(path = "/cities")
    public City postCity(@RequestBody CreateCityDTO dto) {
        CityCreationResult result = application.createCity(dto);
        if (!result.isValid()) {
            throw new ConstraintViolationException(result.getErrors());
        }
        return result.getCity();
    }
}
