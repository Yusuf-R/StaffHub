package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.repository.DepartmentRepository;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import com.naviroq.staffhub.organization.repository.PositionRepository;
import com.naviroq.staffhub.organization.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
    }


    @Override
    public Employee createEmployee(CreateEmployeeCommand command) {

        Department department = departmentRepository.findById(command.departmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Position position = positionRepository.findById(command.positionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));

        Employee manager = null;

        if (command.managerId() != null) {
            manager = employeeRepository.findById(command.managerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        }

        Employee employee = Employee.builder()
                .employeeCode(command.employeeCode())
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
                .build();

        return employeeRepository.save(employee);
    }

    @Override
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

    @Override
    public Employee getEmployeeById(UUID employeeId) {

        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

    }

    @Override
    public List<Employee> listOfEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(UUID employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employeeRepository.delete(employee);

    }
}
