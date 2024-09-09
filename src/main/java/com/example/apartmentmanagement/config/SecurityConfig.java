package com.example.apartmentmanagement.config;

import com.example.apartmentmanagement.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.signing-key}")
    private String signerKey;

    private final String[] PUBLIC_ENDPOINT= {"/api/user", "auth/introspect", "auth/log-in"
                                        , "/api/apartments", "/api/residents", "/api/manager"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(request
        -> request.
              requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
              .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
              .anyRequest().authenticated());
      http.oauth2ResourceServer(oauth2 ->
              oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));
      http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
