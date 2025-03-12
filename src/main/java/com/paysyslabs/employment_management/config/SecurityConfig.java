package com.paysyslabs.employment_management.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
                        .requestMatchers("/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/test/admin").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/test/hr").hasAuthority("ROLE_HR")
                        .requestMatchers("/test/employee").hasAuthority("ROLE_EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            System.out.println("Unauthorized request to: " + request.getRequestURI());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
            defaultConverter.setAuthoritiesClaimName("realm_access.roles");
            defaultConverter.setAuthorityPrefix("ROLE_");

            Collection<String> authorities = defaultConverter.convert(jwt).stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());

            // Extract roles from `resource_access.employment-app.roles`
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            List<String> roles = new ArrayList<>();
            if (resourceAccess != null && resourceAccess.containsKey("employment-app")) {
                Map<String, Object> employmentApp = (Map<String, Object>) resourceAccess.get("employment-app");
                roles = (List<String>) employmentApp.getOrDefault("roles", List.of());
            }

            // Convert Keycloak roles to Spring Security roles
            List<String> mappedRoles = roles.stream()
                    .map(role -> "ROLE_" + role.toUpperCase()) // Ensure prefix is added
                    .collect(Collectors.toList());

            authorities.addAll(mappedRoles);

            // Debugging: Print extracted roles
            System.out.println("JWT Payload: " + jwt.getClaims()); // ✅ Print full JWT claims
            System.out.println("Extracted Roles: " + mappedRoles); // ✅ Print mapped roles

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return converter;
    }
}