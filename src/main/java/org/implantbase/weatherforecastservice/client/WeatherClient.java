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
        String units = unit == null ? TemperatureUnit.METRIC.getApiValue() : unit.getApiValue();

        return webClient.get()
                .uri(uri -> uri
                        .path("/data/2.5/forecast")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("units", units)
                        .queryParam("appid", weatherProperties.getKey())
                        .build())
                .retrieve()
                .bodyToMono(OpenWeatherResponse.class)
                .block();
    }
}
