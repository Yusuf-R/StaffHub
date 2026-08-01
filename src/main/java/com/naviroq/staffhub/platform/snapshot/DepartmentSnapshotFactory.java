package com.naviroq.staffhub.platform.snapshot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naviroq.staffhub.organization.domain.entity.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DepartmentSnapshotFactory {

    private final ObjectMapper objectMapper;

    public JsonNode create(Department department) {

        Map<String, Object> snapshot = new LinkedHashMap<>();

        snapshot.put("id", department.getId());
        snapshot.put("name", department.getName());
        snapshot.put("code", department.getCode());
        snapshot.put("description", department.getDescription());
        snapshot.put("createdAt", department.getCreatedAt());
        snapshot.put("updatedAt", department.getUpdatedAt());

        return objectMapper.valueToTree(snapshot);
    }
}