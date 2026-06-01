package org.implantbase.weatherforecastservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenWeatherResponse {

    private List<ForecastItem> list;

    @Data
    public static class ForecastItem {
        private Main main;
        private String dt_txt;
    }

    @Data
    public static class Main {
        private double temp;
    }
}
