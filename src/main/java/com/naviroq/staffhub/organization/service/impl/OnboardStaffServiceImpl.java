package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.identity.domain.CreateUserCommand;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.service.UserService;
import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.OnboardStaffCommand;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.service.EmployeeService;
import com.naviroq.staffhub.organization.service.OnboardStaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnboardStaffServiceImpl implements OnboardStaffService {

    private final EmployeeService employeeService;
    private final UserService userService;

    @Override
    @Transactional
    public User onboardStaff(OnboardStaffCommand command) {
        log.info("🚀 Onboarding staff...");

        // 1. Build Employee Command from flat OnboardStaffCommand
        CreateEmployeeCommand employeeCommand = new CreateEmployeeCommand(
                command.firstName(),
                command.lastName(),
                command.gender(),
                command.dateOfBirth(),
                command.hireDate(),
                command.phone(),
                command.address(),
                command.bio(),
                command.profilePictureUrl(),
                command.employmentType(),
                command.status(),
                command.departmentId(),
                command.positionId(),
                command.managerId()
        );

        // 2. Create Employee
        Employee employee = employeeService.createEmployee(employeeCommand);
        log.info("✅ Employee created with ID: {}", employee.getId());

        // 3. Create User with the new Employee ID
        CreateUserCommand userCommand = new CreateUserCommand(
                employee.getId(),
                command.username(),
                command.email(),
                command.roleCode(),
                command.password()
        );

        User user = userService.createUser(userCommand);
        log.info("✅ User created for employee: {}", employee.getEmployeeCode());

        return user;
    }
}