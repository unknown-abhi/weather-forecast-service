package org.implantbase.weatherforecastservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<String> handleValidation(Exception ex) {
        // Validation and request binding failures are client-side errors.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Validation error: " + ex.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleUpstream(WebClientResponseException ex) {
        // Translate upstream API failures into gateway-style responses for the caller.
        HttpStatus status = ex.getStatusCode().is4xxClientError()
                ? HttpStatus.BAD_GATEWAY
                : HttpStatus.SERVICE_UNAVAILABLE;

        return ResponseEntity.status(status)
                .body("Upstream weather API error: " + ex.getStatusCode().value() + " " + ex.getStatusText());
    }

    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<String> handleWeatherApi(WeatherApiException ex) {
        // Use a dedicated error when the service receives malformed or incomplete weather data.
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("Weather data error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleFallback(Exception ex) {
        // Keep a final fallback so unexpected bugs still return a controlled response.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + ex.getMessage());
    }
}
