package org.implantbase.weatherforecastservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ForecastItem {

    private Main main;
    private List<Weather> weather;
    private String dt_txt;

}
