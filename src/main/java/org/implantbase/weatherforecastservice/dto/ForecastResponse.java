package org.implantbase.weatherforecastservice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ForecastResponse {

    private final List<ForecastDay> forecast;

    private ForecastResponse(List<ForecastDay> forecast) {
        this.forecast = forecast;
    }

    public static ForecastResponse of(List<ForecastDay> forecast) {
        return new ForecastResponse(forecast);
    }
}
