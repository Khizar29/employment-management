package com.paysyslabs.employment_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/test/admin").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasRole("ADMIN")
                        .requestMatchers("/test/hr").hasRole("HR")
                        .requestMatchers("/test/employee").hasRole("EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
            Collection<String> authorities = defaultConverter.convert(jwt).stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());

            List<String> roles = jwt.getClaimAsMap("realm_access") != null ?
                    (List<String>) jwt.getClaimAsMap("realm_access").get("roles") : List.of();

            List<String> mappedRoles = roles.stream()
                    .map(role -> "ROLE_" + role.toUpperCase()) // Convert Keycloak roles to Spring Security roles
                    .collect(Collectors.toList());

            authorities.addAll(mappedRoles);
            return authorities.stream().map(org.springframework.security.core.authority.SimpleGrantedAuthority::new).collect(Collectors.toList());
        });

        return converter;
    }
}
