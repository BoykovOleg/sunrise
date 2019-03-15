package dev.sunrise.application.event_time.sunrise_api;

import dev.sunrise.domain.GeoCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Can be wrapped by cache proxy for caching success responses. TTL - time until 00:00:00.
 */
@Service
public class SunriseApiGatewayImpl implements SunriseApiGateway {
    /*
     * Can be moved to env variables or .properties.
     */
    private final String API_URL = "https://api.sunrise-sunset.org/json?lat={latitude}&lng={longitude}&date=today&formatted=0";

    private final Logger logger = LoggerFactory.getLogger(SunriseApiGatewayImpl.class);

    private RestTemplate restTemplate;

    public SunriseApiGatewayImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResultDTO get(GeoCoordinates coordinates) {
        SunriseApiResponse response = null;
        try {
             response = restTemplate.getForObject(
                    API_URL,
                    SunriseApiResponse.class,
                    coordinates.getLatitude(),
                    coordinates.getLongitude()
            );
        } catch (HttpClientErrorException e) {
            logger.warn("Sunrise api exception", e);
        }

        if (response == null || !response.isOk()) {

            return ResultDTO.createWithError("Cannot get data, please try later");
        }

        return ResultDTO.createWithResponseResult(response.results);
    }
}
