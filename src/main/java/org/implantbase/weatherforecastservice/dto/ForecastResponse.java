package org.implantbase.weatherforecastservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ForecastResponse {

    private List<ForecastDay> forecast;
}
