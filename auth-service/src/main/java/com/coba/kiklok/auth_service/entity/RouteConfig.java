package com.coba.kiklok.auth_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "route_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "route_id", length = 255)
    private String routeId;
    @Column(name = "path_pattern", length = 255)
    private String pathPattern;
    @Column(name = "uri", length = 255)
    private String uri;
}

