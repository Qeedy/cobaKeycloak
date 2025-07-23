package com.coba.kiklok.auth_service.controller;

import com.coba.kiklok.auth_service.config.KeycloakProperties;
import com.coba.kiklok.auth_service.model.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    private final WebClient webClient;
    private final KeycloakProperties keycloakProperties;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        return webClient.post()
                .uri(keycloakProperties.getTokenUri())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData(USERNAME, request.getUsername())
                        .with(PASSWORD, request.getPassword())
                        .with(GRANT_TYPE, PASSWORD)
                        .with(CLIENT_ID, keycloakProperties.getClientId())
                        .with(CLIENT_SECRET, keycloakProperties.getClientSecret()))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> ResponseEntity.ok().body(body));
    }
}