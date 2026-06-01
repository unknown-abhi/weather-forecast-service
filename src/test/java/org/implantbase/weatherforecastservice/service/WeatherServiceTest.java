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

        // Build mock response
        OpenWeatherResponse res = new OpenWeatherResponse();

        OpenWeatherResponse.Temp temp = new OpenWeatherResponse.Temp();
        temp.setMax(30);
        temp.setMin(20);

        OpenWeatherResponse.Daily d = new OpenWeatherResponse.Daily();
        d.setTemp(temp);
        d.setDt(1717240000);

        // ✅ Add 7 days (better test)
        res.setDaily(List.of(d, d, d, d, d, d, d));

        Mockito.when(client.fetchWeather(1.0, 1.0, "metric")).thenReturn(res);

        WeatherService service = new WeatherService(client);

        ForecastResponse response =
                service.get7DayForecast(1.0, 1.0, "metric");

        // ✅ Assertions
        assertEquals(7, response.getForecast().size());
        assertEquals("30.0°C", response.getForecast().get(0).getHighTemp());
        assertEquals("20.0°C", response.getForecast().get(0).getLowTemp());
    }
}