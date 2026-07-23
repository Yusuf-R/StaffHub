package com.naviroq.staffhub.common.seeder;

import com.naviroq.staffhub.common.constants.DepartmentStandard;
import com.naviroq.staffhub.common.constants.PositionStandard;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.repository.DepartmentRepository;
import com.naviroq.staffhub.organization.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Database Seeder.
 *
 * Runs automatically when the application starts.
 * Populates the database with standard Departments and Positions
 * defined in our constants package.
 *
 * This ensures 100% consistency across all environments (local, test, prod).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    // Inject the repositories so we can check if data exists
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    /**
     * This method is called by Spring Boot right after the app starts.
     */
    @Override
    @Transactional  // Ensures all saves happen in one database transaction
    public void run(String @NonNull ... args) {
        log.info("🚀 Starting Database Seeder...");

        seedDepartments();
        seedPositions();

        log.info("✅ Database Seeder completed successfully!");
    }

    /**
     * Seeds the Departments table using the DepartmentStandard enum.
     *
     * Idempotency check: Only inserts if the department does NOT already exist.
     */
    private void seedDepartments() {
        log.info("🌱 Seeding Departments...");

        int createdCount = 0;
        int skippedCount = 0;

        for (DepartmentStandard standard : DepartmentStandard.values()) {
            String code = standard.getCode();

            // 🔑 IDEMPOTENCY CHECK: Does this department already exist?
            if (departmentRepository.existsByCode(code)) {
                log.debug("⏭️ Department already exists: {} ({})", standard.getName(), code);
                skippedCount++;
                continue; // Skip to the next one
            }

            // ✅ If it doesn't exist, create it using the standard values
            Department department = Department.builder()
                    .name(standard.getName())
                    .code(standard.getCode())
                    .description(standard.getDescription())
                    .build();

            departmentRepository.save(department);
            log.info("✅ Created Department: {} ({})", standard.getName(), code);
            createdCount++;
        }

        log.info("📊 Departments Summary: {} created, {} already existed", createdCount, skippedCount);
    }

    /**
     * Seeds the Positions table using the PositionStandard enum.
     *
     * Idempotency check: Only inserts if the position does NOT already exist.
     */
    private void seedPositions() {
        log.info("🌱 Seeding Positions...");

        int createdCount = 0;
        int skippedCount = 0;

        for (PositionStandard standard : PositionStandard.values()) {
            String title = standard.getTitle();

            // 🔑 IDEMPOTENCY CHECK: Does this position already exist?
            if (positionRepository.existsByTitle(title)) {
                log.debug("⏭️ Position already exists: {}", title);
                skippedCount++;
                continue; // Skip to the next one
            }

            // ✅ If it doesn't exist, create it using the standard values
            Position position = Position.builder()
                    .title(standard.getTitle())
                    .description(standard.getDescription())
                    .build();

            positionRepository.save(position);
            log.info("✅ Created Position: {}", title);
            createdCount++;
        }

        log.info("📊 Positions Summary: {} created, {} already existed", createdCount, skippedCount);
    }
}