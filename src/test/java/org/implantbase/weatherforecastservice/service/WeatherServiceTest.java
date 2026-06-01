package org.implantbase.weatherforecastservice.service;

import org.implantbase.weatherforecastservice.client.WeatherClient;
import org.implantbase.weatherforecastservice.dto.ForecastResponse;
import org.implantbase.weatherforecastservice.dto.OpenWeatherResponse;
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

        OpenWeatherResponse.ForecastItem item = new OpenWeatherResponse.ForecastItem();
        item.setMain(main);
        item.setDt_txt("2024-06-03 12:00:00");

        res.setList(List.of(item, item, item, item, item, item, item));

        Mockito.when(client.fetchWeather(1.0, 1.0, "metric")).thenReturn(res);

        WeatherService service = new WeatherService(client);

        ForecastResponse response = service.get7DayForecast(1.0, 1.0, "metric");

        assertEquals(5, response.getForecast().size());
        assertEquals("30.0 C", response.getForecast().get(0).getHighTemp());
        assertEquals("30.0 C", response.getForecast().get(0).getLowTemp());
        assertEquals("MONDAY", response.getForecast().get(0).getDayOfWeek());
        assertEquals("06/03/2024", response.getForecast().get(0).getDate());
    }
}
