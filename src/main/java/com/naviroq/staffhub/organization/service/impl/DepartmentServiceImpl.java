package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.common.constants.DepartmentStandard;
import com.naviroq.staffhub.common.exception.DuplicateResourceException;
import com.naviroq.staffhub.common.exception.ValidationException;
import com.naviroq.staffhub.organization.domain.department.CreateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.department.UpdateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.repository.DepartmentRepository;
import com.naviroq.staffhub.organization.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Department createDepartment(CreateDepartmentCommand command) {
        log.info("Creating department with code: {}", command.code());

        // ============================================================
        // ENFORCEMENT STEP 1: Validate against the Standard
        // ============================================================

        // 1.1 Check if the code is valid
        if (!DepartmentStandard.isValidCode(command.code())) {
            throw new ValidationException(
                    String.format("Invalid department code: '%s'. Valid codes are: %s",
                            command.code(),
                            DepartmentStandard.getAllCodes())
            );
        }

        // 1.2 Get the standard for this code
        DepartmentStandard standard = DepartmentStandard.fromCode(command.code());

        // 1.3 Check if the name matches the standard (case-insensitive)
        if (!standard.getName().equalsIgnoreCase(command.name())) {
            throw new ValidationException(
                    String.format("Department name does not match the standard. " +
                                    "For code '%s', the standard name is: '%s'. You provided: '%s'",
                            command.code(),
                            standard.getName(),
                            command.name())
            );
        }

        // 1.4 Check if the description matches the standard (case-insensitive)
        if (!standard.getDescription().equalsIgnoreCase(command.description())) {
            throw new ValidationException(
                    String.format("Department description does not match the standard. " +
                                    "For code '%s', the standard description is: '%s'. You provided: '%s'",
                            command.code(),
                            standard.getDescription(),
                            command.description())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 2: Check for duplicates
        // ============================================================

        // 2.1 Check if a department with this code already exists
        if (departmentRepository.existsByCode(command.code())) {
            throw new DuplicateResourceException(
                    String.format("Department with code '%s' already exists", command.code())
            );
        }

        // 2.2 Optional: Also check if a department with this name already exists
        if (departmentRepository.existsByName(command.name())) {
            throw new DuplicateResourceException(
                    String.format("Department with name '%s' already exists", command.name())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 3: Build and Save (Use Standard values for consistency)
        // ============================================================

        // Use the standard values to ensure 100% consistency
        Department department = Department.builder()
                .name(standard.getName())           // Use standard name
                .code(standard.getCode())           // Use standard code
                .description(standard.getDescription()) // Use standard description
                .build();

        Department savedDepartment = departmentRepository.save(department);
        log.info("Successfully created department with code: {}", savedDepartment.getCode());

        return savedDepartment;
    }

    @Override
    @Transactional
    public Department updateDepartment(UUID departmentId, UpdateDepartmentCommand command) {
        log.info("Updating department with ID: {}", departmentId);

        // ============================================================
        // ENFORCEMENT STEP 1: Check if department exists
        // ============================================================
        Department existingDepartment = getDepartmentById(departmentId);

        // ============================================================
        // ENFORCEMENT STEP 2: Validate against the Standard
        // ============================================================

        // 2.1 Check if the new code is valid
        if (!DepartmentStandard.isValidCode(command.code())) {
            throw new ValidationException(
                    String.format("Invalid department code: '%s'. Valid codes are: %s",
                            command.code(),
                            DepartmentStandard.getAllCodes())
            );
        }

        // 2.2 Get the standard for this code
        DepartmentStandard standard = DepartmentStandard.fromCode(command.code());

        // 2.3 Check if the name matches the standard
        if (!standard.getName().equalsIgnoreCase(command.name())) {
            throw new ValidationException(
                    String.format("Department name does not match the standard. " +
                                    "For code '%s', the standard name is: '%s'. You provided: '%s'",
                            command.code(),
                            standard.getName(),
                            command.name())
            );
        }

        // 2.4 Check if the description matches the standard
        if (!standard.getDescription().equalsIgnoreCase(command.description())) {
            throw new ValidationException(
                    String.format("Department description does not match the standard. " +
                                    "For code '%s', the standard description is: '%s'. You provided: '%s'",
                            command.code(),
                            standard.getDescription(),
                            command.description())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 3: Check for duplicates (except itself)
        // ============================================================

        // 3.1 Check if another department already uses this code
        if (!existingDepartment.getCode().equalsIgnoreCase(command.code()) &&
                departmentRepository.existsByCode(command.code())) {
            throw new DuplicateResourceException(
                    String.format("Another department with code '%s' already exists", command.code())
            );
        }

        // 3.2 Check if another department already uses this name
        if (!existingDepartment.getName().equalsIgnoreCase(command.name()) &&
                departmentRepository.existsByName(command.name())) {
            throw new DuplicateResourceException(
                    String.format("Another department with name '%s' already exists", command.name())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 4: Update with Standard values
        // ============================================================
        existingDepartment.setName(standard.getName());
        existingDepartment.setCode(standard.getCode());
        existingDepartment.setDescription(standard.getDescription());

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        log.info("Successfully updated department with ID: {}", updatedDepartment.getId());

        return updatedDepartment;
    }

    @Override
    public Department getDepartmentById(UUID departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));
    }

    @Override
    public List<Department> listDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteDepartment(UUID departmentId) {
        log.info("Deleting department with ID: {}", departmentId);
        Department department = getDepartmentById(departmentId);
        departmentRepository.delete(department);
        log.info("Successfully deleted department with ID: {}", departmentId);
    }
}