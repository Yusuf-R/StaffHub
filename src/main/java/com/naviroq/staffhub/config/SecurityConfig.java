package com.naviroq.staffhub.config;

import com.naviroq.staffhub.identity.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // 👈 Enables @PreAuthorize annotations
@RequiredArgsConstructor

public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        /**
         * http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
         * http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
         * http.formLogin(Customizer.withDefaults()); // form login for the browser
         * http.httpBasic(Customizer.withDefaults()); // this for REST API client -- like Postman , Insomnia
         * http.csrf(customizer -> customizer.disable());
         * return http.build();
         * or
         * return http.
         *                 csrf(AbstractHttpConfigurer::disable)
         *                 .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
         *                 .formLogin(Customizer.withDefaults())
         *                 .httpBasic(Customizer.withDefaults())
         *                 .build();
         */
        http
                // Disable CSRF (we are stateless)
                // .csrf(csrf -> csrf.disable())
                .csrf(AbstractHttpConfigurer::disable)

                // Make sure we don't create sessions (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define which endpoints are public vs protected
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                        "/api/v1/auth/**",          // Login, Refresh, Forgot Password
                                        "/api/v1/public/**",        // Optional: Public company info endpoints
                                        "/api/v1/actuator/**",      // Health - Info checks (optional)
                                        "/api/v3/api-docs/**",      // Swagger docs
                                        "/swagger-ui/**"            // Swagger UI (dev only)
                                ).permitAll()

                                .anyRequest().authenticated()         // Everything else INSIDE /api/v1/ is SECURED
                )

                // Add JWT filter BEFORE the standard UsernamePassword filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

