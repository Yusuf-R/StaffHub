package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.organization.domain.department.CreateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.department.UpdateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.repository.DepartmentRepository;
import com.naviroq.staffhub.organization.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(CreateDepartmentCommand command) {

        Department department = Department.builder()
                .name(command.name())
                .code(command.code())
                .description(command.description())
                .build();

        return departmentRepository.save(department);

    }

    @Override
    public Department updateDepartment(UUID departmentId, UpdateDepartmentCommand command) {

        Department department = getDepartmentById(departmentId);

        department.setName(command.name());
        department.setCode(command.code());
        department.setDescription(command.description());

        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(UUID departmentId) {

        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

    }

    @Override
    public List<Department> listDepartments() {

        return departmentRepository.findAll();

    }
    @Override
    public void deleteDepartment(UUID departmentId) {

        departmentRepository.delete(getDepartmentById(departmentId));

    }

}
