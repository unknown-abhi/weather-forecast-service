package org.implantbase.weatherforecastservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "weather.api")
@Component
public class WeatherProperties {

    private String key;
    private String baseUrl;

}
