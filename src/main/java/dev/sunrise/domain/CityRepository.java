package dev.sunrise.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;

public interface CityRepository extends PagingAndSortingRepository<City, Integer> {

    @Nullable
    City findCityByCoordinates(GeoCoordinates coordinates);

    Iterable<City> findAllByNameIn(Iterable<String> names);
}
