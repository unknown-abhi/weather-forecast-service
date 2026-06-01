package org.implantbase.weatherforecastservice.service;

import lombok.RequiredArgsConstructor;
import org.implantbase.weatherforecastservice.client.WeatherClient;
import org.implantbase.weatherforecastservice.dto.ForecastDay;
import org.implantbase.weatherforecastservice.dto.ForecastResponse;
import org.implantbase.weatherforecastservice.dto.OpenWeatherResponse;
import org.implantbase.weatherforecastservice.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient client;

    public ForecastResponse get7DayForecast(Double lat, Double lon, String unit) {

        OpenWeatherResponse response = client.fetchWeather(lat, lon, unit);

        List<ForecastDay> list = response.getDaily().stream()
                .limit(7)
                .map(d -> ForecastDay.builder()
                        .dayOfWeek(DateUtil.day(d.getDt()))
                        .date(DateUtil.date(d.getDt()))
                        .highTemp(d.getTemp().getMax() + unitSymbol(unit))
                        .lowTemp(d.getTemp().getMin() + unitSymbol(unit))
                        .build())
                .toList();

        return new ForecastResponse(list);
    }

    private String unitSymbol(String unit) {
        return switch (unit) {
            case "metric" -> "°C";
            case "imperial" -> "°F";
            default -> "K";
        };
    }
}