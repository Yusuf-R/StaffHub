package com.naviroq.staffhub.organization.service.impl;

import ch.qos.logback.core.LayoutBase;
import com.fasterxml.jackson.databind.JsonNode;
import com.naviroq.staffhub.common.enums.AuditAction;
import com.naviroq.staffhub.common.enums.AuditEntityType;
import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.EmploymentType;
import com.naviroq.staffhub.common.exception.ValidationException;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.UserRepository;
import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.repository.DepartmentRepository;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import com.naviroq.staffhub.organization.repository.PositionRepository;
import com.naviroq.staffhub.organization.service.EmployeeService;
import com.naviroq.staffhub.organization.service.util.EmployeeCodeGeneratorService;
import com.naviroq.staffhub.platform.service.AuditLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final EmployeeCodeGeneratorService employeeCodeGeneratorService;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;  // 👈 NEW
    private final ObjectMapper objectMapper;

    // Used the AllArgsConstructor to generate the Constructor Automatically

    // ---------- WRITE OPERATIONS ----------

    @Override
    @Transactional
    public Employee createEmployee(CreateEmployeeCommand command) {
        log.info("Creating employee...");

        Department department = departmentRepository.findById(command.departmentId())
                .orElseThrow(() -> new ValidationException("Department not found: " + command.departmentId()));

        Position position = positionRepository.findById(command.positionId())
                .orElseThrow(() -> new ValidationException("Position not found: " + command.positionId()));

        Employee manager = null;
        if (command.managerId() != null) {
            manager = employeeRepository.findById(command.managerId())
                    .orElseThrow(() -> new ValidationException("Manager not found: " + command.managerId()));
        }

        EmploymentType employmentType = command.employmentType() != null
                ? command.employmentType()
                : EmploymentType.FULL_TIME;

        EmploymentStatus status = command.status() != null
                ? command.status()
                : EmploymentStatus.PROBATION;

        String employeeCode = generateUniqueEmployeeCode(department);
        log.info("Generated employee code: {}", employeeCode);

        Employee employee = Employee.builder()
                .employeeCode(employeeCode)
                .firstName(command.firstName())
                .lastName(command.lastName())
                .gender(command.gender())
                .dateOfBirth(command.dateOfBirth())
                .hireDate(command.hireDate())
                .phone(command.phone())
                .address(command.address())
                .department(department)
                .position(position)
                .manager(manager)
                .employmentType(employmentType)
                .bio(command.bio())
                .profilePictureUrl(command.profilePictureUrl())
                .status(status)
                .build();

        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(UUID employeeId, UpdateEmployeeCommand command) {
        Employee employee = getEmployeeById(employeeId);

        Department department = departmentRepository.findById(command.departmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Position position = positionRepository.findById(command.positionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));

        Employee manager = null;
        if (command.managerId() != null) {
            manager = employeeRepository.findById(command.managerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        }

        employee.setFirstName(command.firstName());
        employee.setLastName(command.lastName());
        employee.setGender(command.gender());
        employee.setDateOfBirth(command.dateOfBirth());
        employee.setPhone(command.phone());
        employee.setAddress(command.address());
        employee.setBio(command.bio());
        employee.setStatus(command.status());
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setManager(manager);

        return employeeRepository.save(employee);
    }


    // ---------- READ OPERATIONS (ALL use JOIN FETCH now) ----------

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(UUID employeeId) {
        return employeeRepository.findByIdWithDetails(employeeId)   
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> listOfEmployee() {
        return employeeRepository.findAllWithDetails();   
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllWithDetails();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByDepartment(String department) {
        return employeeRepository.findByDepartmentWithDetails(department);   
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByStatus(String status) {
        EmploymentStatus employmentStatus = EmploymentStatus.valueOf(status.toUpperCase());
        return employeeRepository.findByStatusWithDetails(employmentStatus);   
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByDepartmentAndStatus(String department, String status) {
        EmploymentStatus employmentStatus = EmploymentStatus.valueOf(status.toUpperCase());
        return employeeRepository.findByDepartmentAndStatusWithDetails(department, employmentStatus);   
    }

    // ---------- PAGINATION (Fixed) ----------

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> paginatedGetAllEmployees(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return employeeRepository.findAllWithDetails(pageable);
    }

    @Override
    @Transactional
    public void deleteEmployee(UUID employeeId, String reason) {
        log.info("🗑️ Deleting employee with ID: {}", employeeId);

        // 1. Fetch the employee (with all relationships loaded)
        Employee employee = employeeRepository.findByIdWithDetails(employeeId)
                .orElseThrow(() -> new ValidationException("Employee not found: " + employeeId));

        // 2. 🎯 TAKE A SNAPSHOT before deletion
        JsonNode snapshot = createEmployeeSnapshot(employee);

        // 3. Handle direct reports (set their manager to null)
        List<Employee> reports = employeeRepository.findByManagerId(employeeId);
        for (Employee report : reports) {
            report.setManager(null);
            employeeRepository.save(report);
            log.info("Removed manager from employee: {}", report.getId());
        }

        // 4. Handle the User account (suspend it)
        if (employee.getUser() != null) {
            User user = employee.getUser();
            user.setStatus(com.naviroq.staffhub.common.enums.UserStatus.SUSPENDED);
            userRepository.save(user);
            log.info("Suspended user account for employee: {}", employeeId);
        }

        // 5. 👇 SOFT DELETE the employee
        employee.softDelete(reason);  // Sets deletedAt, deletionReason, status = TERMINATED
        employeeRepository.save(employee);

        // 6. 📝 LOG THE DELETION
        auditLogService.saveAuditLog(
                AuditAction.DELETE,
                AuditEntityType.EMPLOYEE,
                employeeId,
                getCurrentUsername(),
                snapshot,
                null,
                reason,
                getClientIP()
        );

        log.info("✅ Employee soft-deleted and audit log saved. ID: {}", employeeId);
    }

    // ... RESTORE method ...

    @Override
    @Transactional
    public void restoreEmployee(UUID employeeId) {
        // We need to find the employee even if it's deleted, so we can't use the default @Where filter.
        // We'll add a custom query to find by ID ignoring the @Where clause.
        Employee employee = employeeRepository.findDeletedById(employeeId)
                .orElseThrow(() -> new ValidationException("Employee not found or already active: " + employeeId));

        // Restore
        employee.restore();  // Sets deletedAt = null, status = ACTIVE

        // Restore User
        if (employee.getUser() != null) {
            employee.getUser().setStatus(com.naviroq.staffhub.common.enums.UserStatus.ACTIVE);
            userRepository.save(employee.getUser());
        }

        employeeRepository.save(employee);

        auditLogService.saveAuditLog(
                AuditAction.RESTORE,
                AuditEntityType.EMPLOYEE,
                employeeId,
                getCurrentUsername(),
                null,
                createEmployeeSnapshot(employee),
                "Restored from deletion",
                getClientIP()
        );

        log.info("✅ Employee restored. ID: {}", employeeId);
    }

    // ---------- PRIVATE HELPERS ----------

    private JsonNode createEmployeeSnapshot(Employee employee) {
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
            snapshot.put("managerName", employee.getManager().getFirstName() + " " + employee.getManager().getLastName());
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

    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            log.warn("Could not get current username from SecurityContext, using SYSTEM");
            return "SYSTEM";
        }
    }

    private String getClientIP() {
        // In a real app, you'd get this from HttpServletRequest.
        // For now, return a placeholder.
        return "127.0.0.1";
    }

    // ---------- UTILITY ----------

    private String generateUniqueEmployeeCode(Department department) {
        int attempts = 0;
        String employeeCode;

        do {
            employeeCode = employeeCodeGeneratorService.generateEmployeeCode(department);
            attempts++;
        } while (employeeRepository.existsByEmployeeCode(employeeCode) && attempts < 5);

        if (employeeRepository.existsByEmployeeCode(employeeCode)) {
            throw new RuntimeException("Failed to generate unique employee code after 5 attempts");
        }

        return employeeCode;
    }


}