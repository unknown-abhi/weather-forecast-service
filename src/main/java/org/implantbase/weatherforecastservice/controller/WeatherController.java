package org.implantbase.weatherforecastservice.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.implantbase.weatherforecastservice.dto.ForecastResponse;
import org.implantbase.weatherforecastservice.service.WeatherService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@Validated
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/forecast")
    public ForecastResponse getForecast(
            @RequestParam @NotNull Double lat,
            @RequestParam @NotNull Double lon,
            @RequestParam @Pattern(regexp = "metric|imperial|standard") String unit) {
        return weatherService.get7DayForecast(lat, lon, unit);
    }

}
