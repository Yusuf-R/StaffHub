package com.naviroq.staffhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        // http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        http.authorizeHttpRequests(auth -> auth
                // Allow everyone to see the login page and static resources (CSS, images)
                .requestMatchers("/login", "/css/**", "/js/**", "/webjars/**").permitAll()
                // Everything else requires authentication
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")          // Tell Spring to use YOUR login page
                        .defaultSuccessUrl("/dashboard", true) // Redirect here after successful login
                        .permitAll()                  // Allow everyone to access the login page
                )
                .logout(LogoutConfigurer::permitAll                  // Allow everyone to access logout
        );

        return http.build();
    }
}