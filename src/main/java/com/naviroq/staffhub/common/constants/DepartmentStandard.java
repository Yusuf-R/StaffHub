package com.naviroq.staffhub.common.constants;

// DepartmentCode.java — ONLY for seeding, not runtime validation

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Golden Source for Department standards.
 * This is the single source of truth for what Departments are allowed.
 */
@Getter
public enum DepartmentStandard {

    // Define all allowed departments here
    HR("Human Resources", "HR", "Employee Welfare and Recruitment"),
    IT("Information Technology", "IT", "Technology and Infrastructure"),
    SE("Software Engineering", "SE", "Software Development"),
    DEVOPS("DevOps", "DEVOPS", "Cloud Infrastructure"),
    FIN("Finance", "FIN", "Finance Department"),
    MKT("Marketing", "MKT", "Marketing Team"),
    SALES("Sales", "SALES", "Sales Department"),
    SUPPORT("Customer Support", "SUPPORT", "Customer service"),
    RND("Research", "RND", "Research and Innovation"),
    ADMIN("Administration", "ADMIN", "Administrative Services");

    private final String name;
    private final String code;
    private final String description;

    DepartmentStandard(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    // Helper methods for validation

    public static boolean isValidCode(String code) {
        return Arrays.stream(values())
                .anyMatch(d -> d.getCode().equalsIgnoreCase(code));
    }

    public static boolean isValidName(String name) {
        return Arrays.stream(values())
                .anyMatch(d -> d.getName().equalsIgnoreCase(name));
    }

    public static DepartmentStandard fromCode(String code) {
        return Arrays.stream(values())
                .filter(d -> d.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid department code: " + code));
    }

    // Get all valid codes as a list for API responses
    public static List<String> getAllCodes() {
        return Arrays.stream(values())
                .map(DepartmentStandard::getCode)
                .collect(Collectors.toList());
    }
}