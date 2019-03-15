package dev.sunrise.application;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.sunrise.domain.City;
import dev.sunrise.domain.CityRepository;
import dev.sunrise.domain.GeoCoordinates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationCityTest {

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private Application application;

    @Test
    public void getAllCities() {
        when(cityRepository.findAll(any(Pageable.class))).thenReturn(
            new PageImpl<>(
                Arrays.asList(
                        new City("Test", 1.0, 2.0),
                        new City("Test 2", 2.0, 2.0)
                )
            )
        );
        SearchResult<City> result = application.getAllCities(PageRequest.of(0,2));
        assertThat(result.getTotal()).isEqualTo(2);
        assertThat(result.getContent()).isNotNull()
                .isNotEmpty()
                .allMatch(c -> c.getName().toLowerCase().contains("test"));
    }

    @Test
    public void createCity() {
        CreateCityDTO dto = new CreateCityDTO("Test", 1.0, 2.0);
        City city = dto.toCityModel();
        when(cityRepository.save(any(City.class))).thenReturn(city);
        CityCreationResult result = application.createCity(dto);
        assertThat(result).isNotNull();
        assertThat(result.isValid()).isTrue();
        assertThat(result.getCity()).isEqualTo(city);
    }

    @Test
    public void createCityCoordinatesExist() {
        CreateCityDTO dto = new CreateCityDTO("Test", 1.0, 2.0);
        City city = dto.toCityModel();
        City existing = new City("Existing", 1.0, 2.0);
        when(cityRepository.save(any(City.class))).thenReturn(city);
        when(cityRepository.findCityByCoordinates(any(GeoCoordinates.class))).thenReturn(existing);
        CityCreationResult result = application.createCity(dto);
        assertThat(result).isNotNull();
        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors())
                .isNotNull()
                .allMatch(e -> e.getMessageTemplate().contentEquals("{dev.sunrise.application.validators.UniqueGeoCoordinates}"));
    }

    @Test
    public void createCityTooShortName() {
        testName("t");
    }

    @Test
    public void createCityTooLongName() {
        char[] charArray = new char[101];
        Arrays.fill(charArray, 'a');
        testName(new String(charArray));
    }

    private void testName(String name) {
        CreateCityDTO dto = new CreateCityDTO(name, 0.0, 2.0);
        CityCreationResult result = application.createCity(dto);
        assertThat(result).isNotNull();
        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors())
            .isNotNull()
            .allMatch(e -> e.getMessageTemplate().contentEquals("{javax.validation.constraints.Size.message}"));
    }
}
