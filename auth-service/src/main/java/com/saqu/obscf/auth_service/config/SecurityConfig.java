package com.saqu.obscf.auth_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String REALM_ACCESS = "realm_access";
    private static final String ROLES = "roles";
    private static final String ROLE = "ROLE_";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .pathMatchers("/api/customers/**").hasAnyRole(USER, ADMIN)
                        .pathMatchers("/api/admin/**").hasRole(ADMIN)
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                        )
                );

        return http.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        return jwt -> {
            log.info("✅ Claim: {}", jwt.getClaims());
            Map<String, Object> realmAccess = jwt.getClaim(REALM_ACCESS);

            List<String> roles = realmAccess != null
                    ? (List<String>) realmAccess.getOrDefault(ROLES, List.of())
                    : List.of();
            log.info("✅ Extracted roles: {}", roles);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(ROLE + role))
                    .map(authority -> (GrantedAuthority) authority)
                    .toList();
            log.info("✅ Mapped authorities: {}", authorities);

            return Mono.just(new JwtAuthenticationToken(jwt, authorities));
        };
    }
}
