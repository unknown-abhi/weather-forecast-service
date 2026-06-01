# Weather Forecast Service

Spring Boot service that exposes a free weather forecast endpoint backed by the OpenWeather 5 day / 3 hour API.

## Current Implementation

The service accepts latitude, longitude, and a unit system, fetches forecast data from OpenWeather, and returns a simplified response containing only the fields used by the application.

### Main Flow

1. `WeatherController` exposes `GET /api/weather/forecast`.
2. `WeatherService` calls `WeatherClient` to fetch upstream weather data.
3. The service maps the upstream response into a `ForecastResponse`.
4. The response contains up to 5 forecast days pulled from the midday `12:00:00` entries in the free feed.

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
      "date": "06/03/2024",
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
server.port=8080
weather.api.key=YOUR_API_KEY
weather.api.base-url=https://api.openweathermap.org
```

## Local Setup

Before running locally, make sure you have:

- Java 25 installed
- A valid OpenWeather API key in `src/main/resources/application.properties`
- Network access to `https://api.openweathermap.org`

## Run Locally

You can run the service in 3 ways:

1. `./mvnw spring-boot:run`
2. Run `WeatherForecastServiceApplication` directly from your IDE
3. Build the JAR with `./mvnw clean package` and run `java -jar target/weather-forecast-service-0.0.1-SNAPSHOT.jar`

The service starts on port `8080` unless you change `server.port`.

## External API Call

The client currently calls:

`GET {weather.api.base-url}/data/2.5/forecast`

With query parameters:

- `lat`
- `lon`
- `units`
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
- The current implementation filters 12:00:00 entries and returns up to 5 days from the free forecast feed.
- The service method is still named `get7DayForecast`, but the active implementation returns up to 5 days from the free API.
- Errors are handled by a global exception handler that returns `Error: <message>` with HTTP 500.
