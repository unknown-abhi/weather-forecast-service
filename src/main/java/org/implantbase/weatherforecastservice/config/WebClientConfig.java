package org.implantbase.weatherforecastservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientConfig {

    private final WeatherProperties weatherProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(weatherProperties.getBaseUrl())
                .build();
    }
}
