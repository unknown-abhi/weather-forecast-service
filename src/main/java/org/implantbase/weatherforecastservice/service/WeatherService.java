package org.implantbase.weatherforecastservice.service;

import lombok.RequiredArgsConstructor;
import org.implantbase.weatherforecastservice.client.WeatherClient;
import org.implantbase.weatherforecastservice.dto.ForecastDay;
import org.implantbase.weatherforecastservice.dto.ForecastResponse;
import org.implantbase.weatherforecastservice.dto.OpenWeatherResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private static final DateTimeFormatter OUTPUT_DATE =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter OPEN_WEATHER_DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final WeatherClient client;

    public ForecastResponse get7DayForecast(Double lat, Double lon, String unit) {
        OpenWeatherResponse response = client.fetchWeather(lat, lon, unit);

        List<ForecastDay> list = response.getList().stream()
                .filter(d -> d.getDt_txt() != null && d.getDt_txt().contains("12:00:00"))
                .limit(7)
                .map(d -> {
                    LocalDateTime dateTime = LocalDateTime.parse(d.getDt_txt(), OPEN_WEATHER_DATE_TIME);
                    return ForecastDay.builder()
                            .dayOfWeek(dateTime.getDayOfWeek().toString())
                            .date(dateTime.format(OUTPUT_DATE))
                            .highTemp(d.getMain().getTemp() + unitSymbol(unit))
                            .lowTemp(d.getMain().getTemp() + unitSymbol(unit))
                            .build();
                })
                .toList();

        return new ForecastResponse(list);
    }

    private String unitSymbol(String unit) {
        return switch (unit) {
            case "metric" -> " C";
            case "imperial" -> " F";
            default -> "K";
        };
    }
}
