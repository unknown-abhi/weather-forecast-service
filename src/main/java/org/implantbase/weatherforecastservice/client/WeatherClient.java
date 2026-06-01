package org.implantbase.weatherforecastservice.client;

import lombok.RequiredArgsConstructor;
import org.implantbase.weatherforecastservice.config.WeatherProperties;
import org.implantbase.weatherforecastservice.dto.OpenWeatherResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final WebClient webClient;
    private final WeatherProperties weatherProperties;

    public OpenWeatherResponse fetchWeather(Double lat, Double lon, String unit) {
        return webClient.get()
                .uri(uri -> uri
                        .path("/data/3.0/onecall")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("units", unit)
                        .queryParam("exclude", "hourly,minutely,alerts")
                        .queryParam("appid", weatherProperties.getKey())
                        .build())
                .retrieve()
                .bodyToMono(OpenWeatherResponse.class)
                .block();
    }
}
