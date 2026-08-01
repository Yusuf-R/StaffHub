package com.naviroq.staffhub.platform.snapshot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmployeeSnapshotFactory {

    private final ObjectMapper objectMapper;

    public JsonNode create(Employee employee) {

        Map<String, Object> snapshot = new LinkedHashMap<>();

        // Basic info
        snapshot.put("id", employee.getId());
        snapshot.put("employeeCode", employee.getEmployeeCode());
        snapshot.put("firstName", employee.getFirstName());
        snapshot.put("lastName", employee.getLastName());
        snapshot.put("phone", employee.getPhone());
        snapshot.put("address", employee.getAddress());
        snapshot.put("gender", employee.getGender());
        snapshot.put("employmentType", employee.getEmploymentType());
        snapshot.put("hireDate", employee.getHireDate());
        snapshot.put("bio", employee.getBio());
        snapshot.put("profilePictureUrl", employee.getProfilePictureUrl());
        snapshot.put("status", employee.getStatus());

        // Department
        if (employee.getDepartment() != null) {
            snapshot.put("departmentId", employee.getDepartment().getId());
            snapshot.put("departmentName", employee.getDepartment().getName());
        }

        // Position
        if (employee.getPosition() != null) {
            snapshot.put("positionId", employee.getPosition().getId());
            snapshot.put("positionTitle", employee.getPosition().getTitle());
        }

        // Manager
        if (employee.getManager() != null) {
            snapshot.put("managerId", employee.getManager().getId());
            snapshot.put(
                    "managerName",
                    employee.getManager().getFirstName() + " "
                            + employee.getManager().getLastName()
            );
        }

        // User
        if (employee.getUser() != null) {
            snapshot.put("userId", employee.getUser().getId());
            snapshot.put("username", employee.getUser().getUsername());
            snapshot.put("email", employee.getUser().getEmail());
            snapshot.put("userRole", employee.getUser().getRole());
            snapshot.put("userStatus", employee.getUser().getStatus());
        }

        // Timestamps
        snapshot.put("createdAt", employee.getCreatedAt());
        snapshot.put("updatedAt", employee.getUpdatedAt());

        return objectMapper.valueToTree(snapshot);
    }
}