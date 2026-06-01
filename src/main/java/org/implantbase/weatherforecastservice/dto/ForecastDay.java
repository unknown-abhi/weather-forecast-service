package org.implantbase.weatherforecastservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ForecastDay {

    private String dayOfWeek;
    private String date;
    private String highTemp;
    private String lowTemp;
}
