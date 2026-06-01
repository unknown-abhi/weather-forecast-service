# Weather Forecast Service

Spring Boot service that exposes a 7-day weather forecast endpoint backed by the OpenWeather One Call API.

## Current Implementation

The service accepts latitude, longitude, and a unit system, fetches forecast data from OpenWeather, and returns a simplified response containing only the fields used by the application.

### Main Flow

1. `WeatherController` exposes `GET /api/weather/forecast`.
2. `WeatherService` calls `WeatherClient` to fetch upstream weather data.
3. The service maps the upstream response into a `ForecastResponse`.
4. The response contains up to 7 forecast days.

### Forecast Response

`ForecastResponse` is the top-level DTO returned by the API.

```java
public class ForecastResponse {
    private List<ForecastDay> forecast;
}
```

Each `ForecastDay` contains:

```java
private String dayOfWeek;
private String date;
private String highTemp;
private String lowTemp;
```

### Example Response

```json
{
  "forecast": [
    {
      "dayOfWeek": "MONDAY",
      "date": "06/01/2026",
      "highTemp": "34.2 C",
      "lowTemp": "26.1 C"
    }
  ]
}
```

## API Endpoint

### `GET /api/weather/forecast`

Query parameters:

- `lat` - required latitude
- `lon` - required longitude
- `unit` - required unit system, must be one of `metric`, `imperial`, or `standard`

Example:

```bash
GET /api/weather/forecast?lat=12.9716&lon=77.5946&unit=metric
```

## Configuration

The service reads these properties from `src/main/resources/application.properties`:

```properties
spring.application.name=weather-forecast-service
weather.api.key=YOUR_API_KEY
weather.api.base-url=https://api.openweathermap.org
```

## External API Call

The client currently calls:

`GET {weather.api.base-url}/data/3.0/onecall`

With query parameters:

- `lat`
- `lon`
- `units`
- `exclude=hourly,minutely,alerts`
- `appid={weather.api.key}`

## Tech Stack

- Java 25
- Spring Boot 4.0.6
- Spring Web
- Spring WebFlux `WebClient`
- Spring Validation
- Lombok

## Notes

- The service currently returns only the `forecast` list from the upstream response.
- Temperature values are formatted as strings with a unit suffix such as `C`, `F`, or `K`.
- Errors are handled by a global exception handler that returns `Error: <message>` with HTTP 500.
