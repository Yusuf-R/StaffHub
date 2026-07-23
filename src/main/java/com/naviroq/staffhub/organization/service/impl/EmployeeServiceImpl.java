package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.exception.ValidationException;
import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.repository.DepartmentRepository;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import com.naviroq.staffhub.organization.repository.PositionRepository;
import com.naviroq.staffhub.organization.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AutoConfiguration
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

//    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, PositionRepository positionRepository) {
//        this.employeeRepository = employeeRepository;
//        this.departmentRepository = departmentRepository;
//        this.positionRepository = positionRepository;
//    }

    @Override
    public Employee createEmployee(CreateEmployeeCommand command) {
        Department department = departmentRepository.findById(command.departmentId())
                .orElseThrow(() -> new ValidationException("Department not found: " + command.departmentId()));

        // 2. Fetch Position by UUID
        Position position = positionRepository.findById(command.positionId())
                .orElseThrow(() -> new ValidationException("Position not found: " + command.positionId()));

        // 3. Fetch Manager (if provided)
        Employee manager = null;
        if (command.managerId() != null) {
            manager = employeeRepository.findById(command.managerId())
                    .orElseThrow(() -> new ValidationException("Manager not found: " + command.managerId()));
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

    // Paginated
    public Employee paginatedGetAllEmployees(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return (Employee) employeeRepository.findAll(pageable);
    }

    // filter

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

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();  // Built into JpaRepository
    }

    @Override
    public List<Employee> findByDepartment(String department) {
        return employeeRepository.findByDepartment_Name(department);
    }

    @Override
    public List<Employee> findByStatus(String status) {
        EmploymentStatus employmentStatus = EmploymentStatus.valueOf(status.toUpperCase());
        return employeeRepository.findByStatus(employmentStatus);
    }

    @Override
    public List<Employee> findByDepartmentAndStatus(String department, String status) {
        EmploymentStatus employmentStatus = EmploymentStatus.valueOf(status.toUpperCase());
        return employeeRepository.findByDepartment_NameAndStatus(department, employmentStatus);
    }
}
