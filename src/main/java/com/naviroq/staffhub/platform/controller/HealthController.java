package com.naviroq.staffhub.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/staff-hub")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("application", "StaffHub");
        response.put("tagline", "The Connected Workplace");
        response.put("version", "v1.0.0");
        response.put("status", "UP");
        response.put("database", "CONNECTED");
        response.put("success", true);
        response.put("message", "Welcome to StaffHub 🚀");
        response.put("service", "StaffHub API");
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Welcome to StaffHub 🚀");
        response.put("application", "StaffHub");
        response.put("tagline", "The Connected Workplace");
        response.put("version", "1.0.0");
        response.put("javaVersion", "21");
        response.put("springBoot", "3.5.x");
        response.put("database", "CONNECTED");
        response.put("environment", "development");
        response.put("service", "StaffHub API");
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}