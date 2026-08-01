package com.naviroq.staffhub.platform.snapshot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naviroq.staffhub.identity.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserSnapshotFactory {

    private final ObjectMapper objectMapper;

    public JsonNode create(User user) {

        Map<String, Object> snapshot = new LinkedHashMap<>();

        snapshot.put("id", user.getId());
        snapshot.put("username", user.getUsername());
        snapshot.put("email", user.getEmail());
        snapshot.put("role", user.getRole());
        snapshot.put("status", user.getStatus());
        snapshot.put("lastLogin", user.getLastLogin());
        snapshot.put("createdAt", user.getCreatedAt());
        snapshot.put("updatedAt", user.getUpdatedAt());

        return objectMapper.valueToTree(snapshot);
    }
}