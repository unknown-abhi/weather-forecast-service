package org.implantbase.weatherforecastservice.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.implantbase.weatherforecastservice.dto.TemperatureUnit;
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
            // Restrict latitude and longitude to valid geographic ranges at the boundary.
            @RequestParam @NotNull @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") Double lat,
            @RequestParam @NotNull @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") Double lon,
            // TemperatureUnit is converted from the request value by the custom converter.
            @RequestParam TemperatureUnit unit) {
        return weatherService.get7DayForecast(lat, lon, unit);
    }

}
