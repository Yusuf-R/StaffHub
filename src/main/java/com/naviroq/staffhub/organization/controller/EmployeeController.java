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

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee (
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

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployee(
            @PathVariable UUID id
    ) {

        Employee employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(
                employeeMapper.toDto(employee)
        );
    }

//    @GetMapping
//    public ResponseEntity<List<EmployeeSummaryResponse>> listEmployees() {
//
//        List<EmployeeSummaryResponse> response =
//                employeeService.listOfEmployee()
//                        .stream()
//                        .map(employeeMapper::toSummaryDto)
//                        .toList();
//
//        return ResponseEntity.ok(response);
//    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable UUID id
    ) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

}