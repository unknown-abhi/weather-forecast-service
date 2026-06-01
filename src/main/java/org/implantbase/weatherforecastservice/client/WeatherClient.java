package org.implantbase.weatherforecastservice.client;

import lombok.RequiredArgsConstructor;
import org.implantbase.weatherforecastservice.config.WeatherProperties;
import org.implantbase.weatherforecastservice.dto.TemperatureUnit;
import org.implantbase.weatherforecastservice.dto.OpenWeatherResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final WebClient webClient;
    private final WeatherProperties weatherProperties;

    public OpenWeatherResponse fetchWeather(Double lat, Double lon, TemperatureUnit unit) {
        // The free tier uses the 5 day / 3 hour forecast endpoint.
        return webClient.get()
                .uri(uri -> uri
                        .path("/data/2.5/forecast")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        // Default to metric if the caller omits the unit, though the controller normally supplies it.
                        .queryParam("units", unit != null ? unit.getApiValue() : TemperatureUnit.METRIC.getApiValue())
                        .queryParam("appid", weatherProperties.getKey())
                        .build())
                .retrieve()
                .bodyToMono(OpenWeatherResponse.class)
                .block();
    }
}
