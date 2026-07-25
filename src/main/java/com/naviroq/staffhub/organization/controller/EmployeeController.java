package com.naviroq.staffhub.organization.controller;

import com.naviroq.staffhub.organization.domain.employee.dto.CreateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.employee.dto.EmployeeResponseDto;
import com.naviroq.staffhub.organization.domain.employee.dto.UpdateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.mapper.EmployeeMapper;
import com.naviroq.staffhub.organization.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff-hub/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Valid
            @RequestBody
            CreateEmployeeRequest request
    ) {
        Employee employee = employeeService.createEmployee(
                employeeMapper.fromDto(request)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeMapper.toDto(employee));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<Employee> employees = employeeService.listOfEmployee();

        List<EmployeeResponseDto> response = employees.stream()
                .map(employeeMapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Filter
//    @GetMapping
//    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees(
//            @RequestParam(required = false) String department,
//            @RequestParam(required = false) String status
//    ) {
//        List<Employee> employees;
//
//        if (department != null && status != null) {
//            employees = employeeService.findByDepartmentAndStatus(department, status);
//        } else if (department != null) {
//            employees = employeeService.findByDepartment(department);
//        } else if (status != null) {
//            employees = employeeService.findByStatus(status);
//        } else {
//            employees = employeeService.getAllEmployees();
//        }
//
//        List<EmployeeResponseDto> response = employees.stream()
//                .map(employeeMapper::toDto)
//                .toList();
//
//        return ResponseEntity.ok(response);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployee(
            @PathVariable UUID id
    ) {

        Employee employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(
                employeeMapper.toDto(employee)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEmployeeRequest request
    ) {

        Employee employee = employeeService.updateEmployee(
                id,
                employeeMapper.fromDto(request)
        );

        return ResponseEntity.ok(
                employeeMapper.toDto(employee)
        );
    }

    // 👇 DELETE endpoint with Request Parameter for the reason
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable("id") UUID employeeId,
            @RequestParam(value = "reason", required = false, defaultValue = "No reason provided") String reason
    ) {
        employeeService.deleteEmployee(employeeId, reason);
        return ResponseEntity.noContent().build();
    }

    // 👇 NEW: Restore endpoint
    @PatchMapping("/{id}/restore")  // Or @PutMapping
    public ResponseEntity<Void> restoreEmployee(@PathVariable("id") UUID employeeId) {
        employeeService.restoreEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

}