package com.naviroq.staffhub.organization.controller;




import com.naviroq.staffhub.organization.domain.department.dto.CreateDepartmentRequest;
import com.naviroq.staffhub.organization.domain.department.dto.DepartmentResponseDto;
import com.naviroq.staffhub.organization.domain.department.dto.UpdateDepartmentRequest;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.mapper.DepartmentMapper;
import com.naviroq.staffhub.organization.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff-hub/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(
            DepartmentService departmentService,
            DepartmentMapper departmentMapper
    ) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request
    ) {

        Department department = departmentService.createDepartment(
                departmentMapper.fromDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentMapper.toDto(department));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartment(
            @PathVariable UUID id
    ) {

        Department department = departmentService.getDepartmentById(id);

        return ResponseEntity.ok(
                departmentMapper.toDto(department)
        );
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> listDepartments() {

        List<DepartmentResponseDto> response = departmentService.listDepartments()
                .stream()
                .map(departmentMapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDepartmentRequest request
    ) {

        Department department = departmentService.updateDepartment(
                id,
                departmentMapper.fromDto(request)
        );

        return ResponseEntity.ok(
                departmentMapper.toDto(department)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable UUID id
    ) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }

}