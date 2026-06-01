package org.implantbase.weatherforecastservice.config;

import org.implantbase.weatherforecastservice.dto.TemperatureUnit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TemperatureUnitConverter implements Converter<String, TemperatureUnit> {

    @Override
    public TemperatureUnit convert(String source) {
        // Normalize incoming request values before mapping them to the enum.
        return TemperatureUnit.fromValue(source);
    }
}
