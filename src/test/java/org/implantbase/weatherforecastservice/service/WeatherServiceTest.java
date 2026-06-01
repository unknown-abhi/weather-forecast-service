package org.implantbase.weatherforecastservice.service;

import org.implantbase.weatherforecastservice.client.WeatherClient;
import org.implantbase.weatherforecastservice.dto.ForecastResponse;
import org.implantbase.weatherforecastservice.dto.OpenWeatherResponse;
import org.implantbase.weatherforecastservice.dto.TemperatureUnit;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherServiceTest {

    @Test
    void testForecast() {
        WeatherClient client = Mockito.mock(WeatherClient.class);

        OpenWeatherResponse res = new OpenWeatherResponse();

        OpenWeatherResponse.Main main = new OpenWeatherResponse.Main();
        main.setTemp(30);

        OpenWeatherResponse.ForecastItem day1 = new OpenWeatherResponse.ForecastItem();
        day1.setMain(main);
        day1.setDt_txt("2024-06-03 12:00:00");

        OpenWeatherResponse.ForecastItem day2 = new OpenWeatherResponse.ForecastItem();
        day2.setMain(main);
        day2.setDt_txt("2024-06-04 12:00:00");

        OpenWeatherResponse.ForecastItem day3 = new OpenWeatherResponse.ForecastItem();
        day3.setMain(main);
        day3.setDt_txt("2024-06-05 12:00:00");

        OpenWeatherResponse.ForecastItem day4 = new OpenWeatherResponse.ForecastItem();
        day4.setMain(main);
        day4.setDt_txt("2024-06-06 12:00:00");

        OpenWeatherResponse.ForecastItem day5 = new OpenWeatherResponse.ForecastItem();
        day5.setMain(main);
        day5.setDt_txt("2024-06-07 12:00:00");

        res.setList(List.of(day1, day2, day3, day4, day5));

        Mockito.when(client.fetchWeather(1.0, 1.0, TemperatureUnit.METRIC)).thenReturn(res);

        WeatherService service = new WeatherService(client);

        ForecastResponse response = service.get7DayForecast(1.0, 1.0, TemperatureUnit.METRIC);

        assertEquals(5, response.getForecast().size());
        assertEquals("30.0 C", response.getForecast().get(0).getHighTemp());
        assertEquals("30.0 C", response.getForecast().get(0).getLowTemp());
        assertEquals("MONDAY", response.getForecast().get(0).getDayOfWeek());
        assertEquals("06/03/2024", response.getForecast().get(0).getDate());
    }
}
