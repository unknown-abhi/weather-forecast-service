package org.implantbase.weatherforecastservice.service;

import lombok.RequiredArgsConstructor;
import org.implantbase.weatherforecastservice.client.WeatherClient;
import org.implantbase.weatherforecastservice.dto.ForecastDay;
import org.implantbase.weatherforecastservice.dto.ForecastResponse;
import org.implantbase.weatherforecastservice.dto.OpenWeatherResponse;
import org.implantbase.weatherforecastservice.dto.TemperatureUnit;
import org.implantbase.weatherforecastservice.exception.WeatherApiException;
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

    public ForecastResponse get7DayForecast(Double lat, Double lon, TemperatureUnit unit) {
        OpenWeatherResponse response = client.fetchWeather(lat, lon, unit);

        if (response == null || response.getList() == null) {
            // Fail fast if the upstream payload is missing the expected forecast list.
            throw new WeatherApiException("Upstream weather response did not contain forecast data");
        }

        List<ForecastDay> list = response.getList().stream()
                // The free forecast feed provides 3-hour data; noon entries are used as daily summaries.
                .filter(d -> d.getDt_txt() != null && d.getDt_txt().contains("12:00:00"))
                // Keep only one entry per day and cap the result to the supported forecast window.
                .limit(7)
                .map(d -> {
                    if (d.getMain() == null) {
                        // Reject incomplete rows instead of returning partially populated forecast objects.
                        throw new WeatherApiException("Upstream weather response contained an incomplete forecast item");
                    }
                    LocalDateTime dateTime = LocalDateTime.parse(d.getDt_txt(), OPEN_WEATHER_DATE_TIME);
                    return ForecastDay.builder()
                            .dayOfWeek(dateTime.getDayOfWeek().toString())
                            .date(dateTime.format(OUTPUT_DATE))
                            .highTemp(d.getMain().getTemp() + unit.getSymbol())
                            .lowTemp(d.getMain().getTemp() + unit.getSymbol())
                            .build();
                })
                .toList();

        if (list.isEmpty()) {
            throw new WeatherApiException("No forecast entries matched the expected time window");
        }

        return new ForecastResponse(list);
    }
}
