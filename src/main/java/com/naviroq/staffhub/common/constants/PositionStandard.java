package com.naviroq.staffhub.common.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Golden Source for Position standards.
 * This is the single source of truth for what Positions are allowed.
 */
@Getter
public enum PositionStandard {

    // Define all allowed positions here
    BACKEND_DEV("Backend Developer", "Software Developer"),
    FRONTEND_DEV("Frontend Engineer", "Frontend Developer"),
    INFRA_ENGINEER("Infrastructure Engineer", "DevOps Engineer"),
    INFRA_ADMIN("Infrastructure Administrator", "System Administrator"),
    HR("Human resources", "HR Officer"),
    FINANCE("Finance", "Finance Officer"),
    PRODUCT("Product Management", "Product Manager"),
    DESIGN("Design", "UI/UX Designer"),
    QA("Software Testing", "QA Engineer"),
    TECH_SUPPORT("Technical Support", "IT Support");

    private final String title;
    private final String description;

    PositionStandard(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Helper methods for validation

    public static boolean isValidTitle(String title) {
        return Arrays.stream(values())
                .anyMatch(p -> p.getTitle().equalsIgnoreCase(title));
    }

    public static PositionStandard fromTitle(String title) {
        return Arrays.stream(values())
                .filter(p -> p.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid position title: " + title));
    }

    public static List<String> getAllTitles() {
        return Arrays.stream(values())
                .map(PositionStandard::getTitle)
                .collect(Collectors.toList());
    }
}