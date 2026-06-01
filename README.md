# Weather Forecast Service

Spring Boot service that exposes a weather forecast endpoint backed by the free OpenWeather 5 day / 3 hour API.

## Current Implementation

The service accepts latitude, longitude, and a temperature unit, fetches forecast data from OpenWeather, and returns a simplified array of forecast objects. The current implementation uses the free forecast endpoint, not One Call, because One Call requires a subscription.

### Flow

1. `WeatherController` exposes `GET /api/weather/forecast`.
2. The controller validates latitude and longitude ranges before invoking the service.
3. `TemperatureUnitConverter` converts the `unit` query parameter into the `TemperatureUnit` enum.
4. `WeatherService` calls `WeatherClient` to fetch upstream weather data.
5. The service filters midday entries and maps them into `ForecastDay` objects.
6. `ForecastResponse` returns the final `forecast` list.

### API Endpoint

`GET /api/weather/forecast`

Query parameters:

- `lat` - required latitude, must be between `-90.0` and `90.0`
- `lon` - required longitude, must be between `-180.0` and `180.0`
- `unit` - required temperature unit, must be one of `metric`, `imperial`, or `standard`

Example:

```bash
GET /api/weather/forecast?lat=12.9716&lon=77.5946&unit=metric
```

### Response Shape

The response contains a single top-level property:

```json
{
  "forecast": [
    {
      "dayOfWeek": "MONDAY",
      "date": "06/03/2024",
      "highTemp": "30.0 C",
      "lowTemp": "30.0 C"
    }
  ]
}
```

Each forecast object contains:

- `dayOfWeek`
- `date` in `MM/dd/yyyy` format
- `highTemp`
- `lowTemp`

### Current Behavior

- The service uses the free OpenWeather `/data/2.5/forecast` endpoint.
- It filters forecast entries that match `12:00:00`.
- It maps up to 7 midday entries, but the free feed typically provides up to 5 forecast days.
- Temperatures are returned with a unit suffix such as `C`, `F`, or `K`.
- The method name is still `get7DayForecast`, but the active implementation is based on the free forecast feed.

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

## Error Handling

The application uses structured error handling:

- Validation issues return `400 Bad Request`
- Upstream API issues return `502 Bad Gateway` or `503 Service Unavailable`
- Weather payload issues return `502 Bad Gateway`
- Unexpected failures return `500 Internal Server Error`

## Tech Stack

- Java 25
- Spring Boot 4.0.6
- Spring Web
- Spring WebFlux `WebClient`
- Spring Validation
- Lombok

## Notes

- The UI is available in `src/main/resources/static/index.html`.
- Unit tests are included for the weather service mapping.
