package com.coba.kiklok.auth_service.router;

import com.coba.kiklok.auth_service.entity.RouteConfig;
import com.coba.kiklok.auth_service.repository.RouteConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public  class  CustomRouteLocator {
    private final RouteConfigRepository routeConfigRepository;
    @Bean
    public RouteLocator dynamicRoutes (RouteLocatorBuilder builder) {
        List<RouteConfig> routeConfigs = routeConfigRepository.findAll();
        log.info( "Route from DB: {}" , routeConfigs);
        RouteLocatorBuilder.Builder routes  = builder.routes();
        routeConfigs.forEach(rc -> routes.route(rc.getRouteId(), r -> r
                .path(rc.getPathPattern())
                .uri(rc.getUri())));
        return routes.build();
    }
}
