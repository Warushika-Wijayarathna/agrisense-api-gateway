package com.agrisense.apigateway;

import java.util.List;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayRoutingConfig {

    @Bean
    RouteLocator agrisenseRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("device-service", route -> route
                        .path("/api/devices", "/api/devices/**")
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("lb://DEVICE-SERVICE"))
                .route("sensor-data-service", route -> route
                        .path("/api/sensor-data", "/api/sensor-data/**")
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("lb://SENSOR-DATA-SERVICE"))
                .route("alert-service", route -> route
                        .path("/api/alerts", "/api/alerts/**")
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("lb://ALERT-SERVICE"))
                .route("media-service", route -> route
                        .path("/api/media", "/api/media/**")
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("lb://MEDIA-SERVICE"))
                .build();
    }

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
