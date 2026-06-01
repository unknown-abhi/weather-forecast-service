package org.implantbase.weatherforecastservice.dto;

import lombok.Getter;

@Getter
public enum TemperatureUnit {
    METRIC("metric", " C"),
    IMPERIAL("imperial", " F"),
    STANDARD("standard", "K");

    private final String apiValue;
    private final String symbol;

    TemperatureUnit(String apiValue, String symbol) {
        this.apiValue = apiValue;
        this.symbol = symbol;
    }

    public static TemperatureUnit fromValue(String value) {
        // Convert the raw query parameter into a validated application enum.
        if (value == null) {
            throw new IllegalArgumentException("Temperature unit is required");
        }

        return switch (value.trim().toLowerCase()) {
            case "metric" -> METRIC;
            case "imperial" -> IMPERIAL;
            case "standard" -> STANDARD;
            default -> throw new IllegalArgumentException(
                    "Invalid unit. Allowed values are metric, imperial, standard");
        };
    }
}
