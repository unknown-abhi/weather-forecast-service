package org.implantbase.weatherforecastservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenWeatherResponse {

    private List<Daily> daily;

    @Data
    public static class Daily {
        private long dt;
        private Temp temp;
    }

    @Data
    public static class Temp {
        private double min;
        private double max;
    }
}
